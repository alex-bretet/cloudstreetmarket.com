package edu.zipcloud.cloudstreetmarket.api.controllers;

import static edu.zipcloud.cloudstreetmarket.core.enums.Role.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static edu.zipcloud.cloudstreetmarket.core.i18n.I18nKeys.*;
import static edu.zipcloud.cloudstreetmarket.api.controllers.UsersController.*;
import static edu.zipcloud.cloudstreetmarket.api.config.WebSocketConfig.*;

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

import edu.zipcloud.cloudstreetmarket.api.services.CurrencyExchangeService;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;
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
	private CurrencyExchangeService currencyExchangeService;
	
	@RequestMapping(value="/login", method=POST)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Identifies the provided user")
	public void login(@RequestBody User user, 
							@RequestHeader(value="Spi", required=false) String guid, 
								@RequestHeader(value="OAuthProvider", required=false) String provider, 
									HttpServletResponse response){
		user = communityService.identifyUser(user);
		if(isNotBlank(guid)){
			usersConnectionRepository.bindSocialUserToUser(guid, user, provider);
		}
    	communityService.signInUser(user);
	}
	
	@RequestMapping(method=POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a user account")
	public void create(@Valid @RequestBody User user, 
								@RequestHeader(value="Spi", required=false) String guid, 
									@RequestHeader(value="OAuthProvider", required=false) String provider,
										HttpServletResponse response) throws IllegalAccessException{
		if(isNotBlank(guid)){
			if(isUnknownToProvider(guid, provider) || usersConnectionRepository.isSocialUserAlreadyRegistered(guid)){
				throw new AccessDeniedException(guid+" @ "+ provider);
			}
			
			user = communityService.createUserWithBalance(user, new Role[]{ROLE_BASIC, ROLE_OAUTH2}, BigDecimal.valueOf(20000L));
			messagingTemplate.convertAndSend(TOPIC_ACTIVITY_FEED_PATH, new UserActivityDTO(user.getActions().iterator().next()));
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
			messagingTemplate.convertAndSend("/topic/actions", new UserActivityDTO(user.getActions().iterator().next()));
			communityService.signInUser(user);
		}
		
		response.setHeader(MUST_REGISTER_HEADER, FALSE);
		response.setHeader(LOCATION_HEADER, USERS_PATH + user.getId());
	}
	
	@RequestMapping(method=PUT)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Updates a user account")
	public void update(@Valid @RequestBody User user, BindingResult result){
		ValidatorUtil.raiseFirstError(result);
		prepareUser(user);
		communityService.updateUser(user);
	}
	
	@RequestMapping(method=GET)
	@ResponseStatus(HttpStatus.PARTIAL_CONTENT)
	@ApiOperation(value = "List user accounts", notes = "")
	public Page<UserDTO> search(
			@Or({
                @Spec(params="cn", path="id", spec=LikeIgnoreCase.class)}	
			) @ApiIgnore Specification<User> spec,
            @ApiParam(value="Contains filter") @RequestParam(value="cn", defaultValue="", required=false) String contain, 
            @ApiIgnore @PageableDefault(size=10, page=0, sort={"balance"}, direction=Direction.DESC) Pageable pageable){
		return communityService.search(spec, pageable);
	}

	@RequestMapping(value="/{username}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Details one account", notes = "")
	public UserDTO get(@PathVariable String username){
		return communityService.getUser(username);
	}
	
	@RequestMapping(value="/{username}", method=DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete user account", notes = "")
	public void delete(@PathVariable String username){
		communityService.delete(username);
	}
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new UserValidator());
    }

	private boolean isUnknownToProvider(String guid, String providerId) {
		Preconditions.checkArgument(isNotBlank(providerId), bundle.get(I18N_USER_GUID_UNKNOWN_TO_PROVIDER));
		
		Set<String> results = usersConnectionRepository.findUserIdsConnectedTo(providerId, Sets.newHashSet(guid));
		if(results==null || results.isEmpty()){
			return true;
		}
		return false;
	}
	
	private void prepareUser(User user){
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