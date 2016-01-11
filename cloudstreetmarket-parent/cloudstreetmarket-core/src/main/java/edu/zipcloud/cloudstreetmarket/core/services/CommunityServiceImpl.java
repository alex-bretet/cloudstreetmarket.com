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
package edu.zipcloud.cloudstreetmarket.core.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import com.google.common.base.Preconditions;

import edu.zipcloud.cloudstreetmarket.core.daos.ActionRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.UserRepository;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.AccountActivity;
import edu.zipcloud.cloudstreetmarket.core.entities.Action;
import edu.zipcloud.cloudstreetmarket.core.entities.Authority;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedLanguage;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;
import edu.zipcloud.cloudstreetmarket.core.util.TransactionUtil;

@Service(value="communityServiceImpl")
@Transactional(propagation=Propagation.REQUIRED)
@PropertySource(value="file:${user.home}/app/cloudstreetmarket.properties")
public class CommunityServiceImpl implements CommunityService {

	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private String[] reservedUserNames = {"leaderboard"};

    private static final String RANDOM_PASSWORD_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-_!$*";
    private static final int RANDOM_PASSWORD_LENGTH = 12;
    
	@Override
	public Page<UserActivityDTO> getPublicActivity(Pageable pageable) {
		Page<Action> actions = actionRepository.findAll(pageable);
		List<UserActivityDTO> result = transform(actions);
		return new PageImpl<>(result, pageable, actions.getTotalElements());
	}

	private List<UserActivityDTO> transform(Iterable<Action> actions){
		List<UserActivityDTO> result = new LinkedList<UserActivityDTO>();
		actions.forEach(
				a -> {
					if(a instanceof AccountActivity){
						UserActivityDTO accountActivity = new UserActivityDTO(
								a.getUser().getUsername(),
								a.getUser().getProfileImg(),
								((AccountActivity)a).getType(),
								((AccountActivity)a).getDate(),
								a.getId()
						);
						accountActivity.setSocialReport(a.getSocialEventAction());
						result.add(accountActivity);
					}
					else if(a instanceof Transaction){
						UserActivityDTO transaction = new UserActivityDTO((Transaction)a);
						transaction.setSocialReport(a.getSocialEventAction());
						result.add(transaction);
					}
				}
			);
		return result;
	}

	@Override
	public User findByUserName(String userName) {
		return userRepository.findOne(userName);
	}
	
	@Override
	public User createUser(User user, Role role) {
		if(findByUserName(user.getUsername()) != null || Arrays.asList(reservedUserNames).contains(user.getUsername())){
			throw new ConstraintViolationException("The provided user name already exists!", null, null);
		}
		user.addAuthority(new Authority(user, role));
		user.addAction(new AccountActivity(user, UserActivityType.REGISTER, new Date()));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@Override
	public User createUser(User user, Role[] roles) {
		if(findByUserName(user.getUsername()) != null || Arrays.asList(reservedUserNames).contains(user.getUsername())){
			throw new ConstraintViolationException("The provided user name already exists!", null, null);
		}
		
		Arrays.asList(roles).forEach(r -> {
			user.addAuthority(new Authority(user, r));
		});
		user.addAction(new AccountActivity(user, UserActivityType.REGISTER, new Date()));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@Override
	public User updateUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public User createUserWithBalance(User user, Role[] roles, BigDecimal balance) {
		user.setBalance(balance);
		user.setLanguage(SupportedLanguage.EN);
		return createUser(user, roles);
	}

	@Override
	public Page<UserDTO> search(Specification<User> spec, Pageable pageable) {
		Page<User> users = userRepository.findAll(spec, pageable);
		List<UserDTO> result = users.getContent().stream()
			.map(u -> {
				UserDTO userDTO = new UserDTO(u);
				hideSensitiveFieldsIfNecessary(userDTO);
		        return new UserDTO(u);
			})
			.collect(Collectors.toCollection(LinkedList::new));
		return new PageImpl<>(result, pageable, users.getTotalElements());
	}

	@Override
	public User findOne(String username) {
		return userRepository.findOne(username);
	}
	
	@Override
	@Secured({"ROLE_ADMIN", "ROLE_SYSTEM"})
	public void delete(String userName) {
		userRepository.delete(userName);
	}
	
	@Override
	@Secured("ROLE_ADMIN")
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	@Override
	public Page<UserDTO> getLeaders(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		List<UserDTO> result = users.getContent().stream()
				.map(u -> {
					UserDTO userDTO = new UserDTO(u);
					hideSensitiveFieldsIfNecessary(userDTO);
			        return new UserDTO(u);
				})
				.collect(Collectors.toCollection(LinkedList::new));
			return new PageImpl<>(result, pageable, users.getTotalElements());
	}
	
	@Override
	public UserDTO getUser(String username) {
		User user = userRepository.findOne(username);
		if(user == null){
			throw new ResourceNotFoundException();
		}
		UserDTO userDTO = new UserDTO(user);
		hideSensitiveFieldsIfNecessary(userDTO);
		return userDTO;
	}
	
	private  void hideSensitiveInformation(UserDTO userDTO){
		userDTO.setPassword("hidden");
		userDTO.setEmail("hidden");
	}
	
	private  UserDTO hideSensitiveFieldsIfNecessary(UserDTO userDTO){
		Preconditions.checkNotNull(userDTO);
		if(AuthenticationUtil.isThePrincipal(userDTO.getId())){
			return userDTO;
		}
		else{
			hideSensitiveInformation(userDTO);
			return userDTO;
		}
	}
	
	@Override
	public User identifyUser(User user) {
		Preconditions.checkArgument(user.getPassword() != null, "The provided password cannot be null!");
		Preconditions.checkArgument(StringUtils.isNotEmpty(user.getPassword()), "The provided password cannot be empty!");
		
		User retreivedUser = userRepository.findOne(user.getUsername());
		if(!passwordEncoder.matches(user.getPassword(), retreivedUser.getPassword())){
			throw new BadCredentialsException("No match has been found with the provided credentials!");
		}
		return retreivedUser;
	}

	@Override
	public User getUserByEmail(String email) {
		Set<User> result = userRepository.findByEmail(email);
		return result != null ? result.stream().findFirst().orElse(null) : null;
	}

	@Override
	public User createUser(String nickName, String email, String password) {
		User user = new User(nickName, passwordEncoder.encode(password), email, null, true, true, true, true, null, null, null, null);
		return userRepository.save(user);
	}
	
	@Override
	public Set<Authority> createAuthorities(Role[] roles) {
		Set<Authority> authorities = new HashSet<Authority>();
		Authority auth = new Authority();
		Arrays.asList(roles).forEach(r -> {
			auth.setAuthority(r);
		});
		authorities.add(auth);		
		return authorities;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findByLogin(String login) {
		return userRepository.findOne(login);
	}

	@Override
	public void registerUser(User user) {
		String password = user.getPassword();
        if (password == null) {
            password = generatePassword();
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
	}

	@Override
    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < RANDOM_PASSWORD_LENGTH; i++) {
            int charIndex = (int) (Math.random() * RANDOM_PASSWORD_CHARS.length());
            char randomChar = RANDOM_PASSWORD_CHARS.charAt(charIndex);
            password.append(randomChar);
        }
        return password.toString();
    }

	@Override
	public Authentication signInUser(User user) {
	    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    return authentication;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		User user = userRepository.findOne(username);
	    Authentication auth;
	    
		if(user != null){
			return user;
		}
		
       SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            auth = securityContext.getAuthentication();
	        if (auth != null) {
	            Object principal = auth.getPrincipal();
	            if (principal instanceof User) {
	            	return (User) principal;
	            }
	        }
        }
		
        //fallback
        throw new ResourceAccessException("No found user for username: "+username);
	}

	@Override
	public boolean isAffordableToUser(int quantity, StockQuote quote, User user, @Nullable CurrencyExchange currencyExchange) {
		BigDecimal priceInUserCurrency = TransactionUtil.getPriceInUserCurrency(quote, UserActivityType.BUY, quantity, user, currencyExchange);
		return user.getBalance().compareTo(priceInUserCurrency) >= 0;
	}

	@Override
	public void alterUserBalance(int quantity, StockQuote quote, User user, UserActivityType type, @Nullable CurrencyExchange currencyExchange) {
		BigDecimal priceInUserCurrency = TransactionUtil.getPriceInUserCurrency(quote, type, quantity, user, currencyExchange);
		
		if(UserActivityType.BUY.equals(type)){
			user.setBalance(user.getBalance().add(priceInUserCurrency.negate()));
		}
		else if(UserActivityType.SELL.equals(type)){
			user.setBalance(user.getBalance().add(priceInUserCurrency));
		}
		
		userRepository.save(user);
	}

	@Override
	public User hydrate(User user) {
		return user;
	}
}
