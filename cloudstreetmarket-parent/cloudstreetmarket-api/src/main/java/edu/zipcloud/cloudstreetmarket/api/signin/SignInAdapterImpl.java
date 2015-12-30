/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.zipcloud.cloudstreetmarket.api.signin;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;

@Transactional(propagation = Propagation.REQUIRED)
@PropertySource("classpath:application.properties")
public class SignInAdapterImpl implements SignInAdapter{

	@Autowired
	private UserRepository userRepository;
	
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
			user = new User(userId, communityService.generatePassword(), null, true, true, true, true, communityService.createAuthorities(new Role[]{Role.ROLE_BASIC, Role.ROLE_OAUTH2}));
		}
		else{
			//Here we have a successful previous oAuth authentication
			//Also the user is already registered
			//Only the guid will be sent back
			List<SocialUser> socialUsers = socialUserRepository.findByProviderUserIdOrUserId(userId, userId);
			if(CollectionUtils.isNotEmpty(socialUsers)){
				//For now we only deal with Yahoo!
				//Later we will have to get the provider the user has selected to login with
				view = successView.concat("?spi="+socialUsers.get(0).getProviderUserId());
			}
		}
		
	    communityService.signInUser(user);
	    return view;
	}
}