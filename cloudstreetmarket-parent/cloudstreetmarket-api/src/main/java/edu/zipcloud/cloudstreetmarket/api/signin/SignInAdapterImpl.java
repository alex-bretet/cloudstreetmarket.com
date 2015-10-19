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
package edu.zipcloud.cloudstreetmarket.api.signin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;

import edu.zipcloud.cloudstreetmarket.core.daos.SocialUserRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.UserRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.SocialUser;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.helpers.CommunityServiceHelper;
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;

@Transactional(propagation = Propagation.REQUIRED)
public class SignInAdapterImpl implements SignInAdapter{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommunityServiceHelper communityServiceHelper;

	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private SocialUserRepository socialUserRepository;
	
	@Value("${oauth.success.view}")
    private String successView;
	
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
	    User user = userRepository.findOne(userId);
	    String view = null;
		if(user == null){
			//temporary user for Spring Security
			//won't be persisted
			user = new User(userId, communityServiceHelper.generatePassword(), null, null, true, true, true, true, communityService.createAuthorities(new Role[]{Role.ROLE_BASIC, Role.ROLE_OAUTH2}), null, null, null, null);
		}
		else{
			//Here we have a successful previous oAuth authentication
			//Also the user is already registered
			//Only the guid will be sent back
			
			List<SocialUser> socialUsers = socialUserRepository.findByProviderUserIdOrUserId(userId, userId);
			if(socialUsers!= null && !socialUsers.isEmpty()){
				//For now we only deal with Yahoo!
				//Later we will have to get the provider the user has selected to login with
				view = successView.concat("?spi="+socialUsers.get(0).getProviderUserId());
			}
		}
		
	    communityService.signInUser(user);
	    return view;

	}
	
}