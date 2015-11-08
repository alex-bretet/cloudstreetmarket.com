/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.api.controllers;

import static edu.zipcloud.cloudstreetmarket.api.controllers.UsersController.USERS_PATH;
import static edu.zipcloud.cloudstreetmarket.core.enums.Role.ROLE_BASIC;
import static edu.zipcloud.cloudstreetmarket.core.enums.Role.ROLE_OAUTH2;
import static edu.zipcloud.cloudstreetmarket.core.i18n.I18nKeys.I18N_USER_GUID_UNKNOWN_TO_PROVIDER;
import static edu.zipcloud.cloudstreetmarket.core.util.Constants.FALSE;
import static edu.zipcloud.cloudstreetmarket.core.util.Constants.LOCATION_HEADER;
import static edu.zipcloud.cloudstreetmarket.core.util.Constants.MUST_REGISTER_HEADER;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.math.BigDecimal;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import edu.zipcloud.cloudstreetmarket.api.services.CurrencyExchangeServiceOnline;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;
import edu.zipcloud.cloudstreetmarket.core.util.Constants;
import edu.zipcloud.cloudstreetmarket.core.util.ValidatorUtil;
import edu.zipcloud.cloudstreetmarket.core.validators.UserValidator;

@Api(value = "users", description = "Cloudstreet Market users") // Swagger annotation
@RestController
@RequestMapping(value=USERS_PATH, produces={"application/xml", "application/json"})
public class UsersController extends CloudstreetApiWCI{

	public static final String USERS_PATH = "/users";
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private ConnectionRepository connectionRepository;

	@Autowired
	private CurrencyExchangeServiceOnline currencyExchangeService;

	@RequestMapping(method=POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a user account")
	public void create(@Valid @RequestBody User user, 
								@RequestHeader(value="Spi", required=false) String guid, 
									@RequestHeader(value="OAuthProvider", required=false) String provider,
										HttpServletResponse response) throws IllegalAccessException{
		if(isNotBlank(guid)){
			if(isGuidUnknownToProvider(guid, provider) || usersConnectionRepository.isSocialUserAlreadyRegistered(guid)){
				throw new AccessDeniedException(guid+" @ "+ provider);
			}
			
			user = communityService.createUserWithBalance(user, new Role[]{ROLE_BASIC, ROLE_OAUTH2}, BigDecimal.valueOf(20000L));

			messagingTemplate.convertAndSend(Constants.WS_TOPIC_ACTIVITY_FEED_PATH, new UserActivityDTO(user.getActions().iterator().next()));

			usersConnectionRepository.bindSocialUserToUser(guid, user, provider);
			communityService.signInUser(user);
			
			if(!SupportedCurrency.USD.equals(user.getCurrency())){
				CurrencyExchange currencyExchange = currencyExchangeService.gather("USD"+user.getCurrency().name()+"=X");
				user.setBalance(currencyExchange.getDailyLatestValue().multiply(BigDecimal.valueOf(20000L)));
			}

			communityService.save(user);
		}
		else{
			user = communityService.createUser(user, ROLE_BASIC);
			messagingTemplate.convertAndSend(Constants.AMQP_USER_ACTIVITY_QUEUE, new UserActivityDTO(user.getActions().iterator().next()));
			communityService.signInUser(user);
		}
		
		response.setHeader(MUST_REGISTER_HEADER, FALSE);
		response.setHeader(LOCATION_HEADER, USERS_PATH + "/" + user.getId());
	}
	
	@RequestMapping(value="/{username}", method=PUT)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Updates a user account")
	public void update(@Valid @RequestBody User user, BindingResult result){
		ValidatorUtil.raiseFirstError(result);
		prepareUserForUpdate(user);
		communityService.updateUser(user);
	}
	
	@RequestMapping(method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List user accounts", notes = "")
	public Page<UserDTO> search(
			@Or({
                @Spec(params="cn", path="id", spec=LikeIgnoreCase.class)}	
			) @ApiIgnore Specification<User> spec,
            @ApiParam(value="Contains filter") @RequestParam(value="cn", defaultValue="", required=false) String contain, 
            @ApiIgnore @PageableDefault(size=10, page=0, sort={"balance"}, direction=Direction.DESC) Pageable pageable){
		return communityService.search(spec, pageable);
	}
	
	@RequestMapping(value="/leaderboard", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Leaderboard", notes = "")
	public Page<UserDTO> getLeaders(
            @ApiIgnore @PageableDefault(size=9, page=0, sort={"balanceUSD"}, direction=Direction.DESC) Pageable pageable){
		return communityService.getLeaders(pageable);
	}

	@RequestMapping(value="/{username}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Details one account", notes = "")
	public UserDTO get(@PathVariable String username){
		return communityService.getUser(username);
	}

	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new UserValidator());
    }

	private boolean isGuidUnknownToProvider(String guid, String providerId) {
		Preconditions.checkArgument(isNotBlank(providerId), bundle.get(I18N_USER_GUID_UNKNOWN_TO_PROVIDER));
		
		Set<String> results = usersConnectionRepository.findUserIdsConnectedTo(providerId, Sets.newHashSet(guid));
		if(results==null || results.isEmpty()){
			return true;
		}
		return false;
	}
	
	private void prepareUserForUpdate(User user){
		User existingUser = communityService.findOne(user.getId());
		if(!existingUser.getCurrency().equals(user.getCurrency())){
			CurrencyExchange currencyExchange = currencyExchangeService.gather(existingUser.getCurrency().name() + user.getCurrency().name()+"=X");
			BigDecimal change = BigDecimal.valueOf(1L);
			if(currencyExchange != null 
					&& currencyExchange.getAsk() != null 
						&& currencyExchange.getAsk().compareTo(BigDecimal.ZERO) > 0 ){
				change = currencyExchange.getAsk();
			}
			//Let's say 2.5% virtual charge applied for now 
			user.setBalance(change.multiply(existingUser.getBalance()).multiply(BigDecimal.valueOf(0.975)));
		}
		else{
			user.setBalance(existingUser.getBalance());
		}
	}
}