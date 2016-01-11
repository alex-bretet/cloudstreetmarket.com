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

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import edu.zipcloud.cloudstreetmarket.core.daos.SocialUserConnectionRepositoryImpl;
import edu.zipcloud.cloudstreetmarket.core.daos.SocialUserRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.UserRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.SocialUser;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

@Transactional(readOnly = true)
public class SocialUserServiceImpl implements SocialUserService {

    @Autowired
    private SocialUserRepository socialUserRepository;
   
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;
    
	@Autowired
	private UserRepository userRepository;
    
	private TextEncryptor textEncryptor = Encryptors.noOpText();
	
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        return socialUserRepository.findUserIdsByProviderIdAndProviderUserId(key.getProviderId(), key.getProviderUserId());
    }

    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        return Sets.newHashSet(socialUserRepository.findUserIdsByProviderIdAndProviderUserIds(providerId, providerUserIds));
    }

    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new SocialUserConnectionRepositoryImpl(
                userId,
                socialUserRepository,
                connectionFactoryLocator,
                textEncryptor);
    }

    @Transactional
	public SocialUser bindSocialUserToUser(String guid, User user, String providerId) {
		SocialUser socialUser = socialUserRepository.findFirstByUserIdAndProviderId(guid, providerId);
		socialUser.setUserId(user.getUsername());
		return socialUserRepository.save(socialUser);
	}
	
	@Override
	public SocialUser getRegisteredSocialUser(String guid) {
		return findSocialUserRegistered(guid).orElse(null);
	}

	@Override
	public boolean isSocialUserAlreadyRegistered(String guid) {
		return findSocialUserRegistered(guid).isPresent();
	}

	private Optional<SocialUser> findSocialUserRegistered(String guid){
		return socialUserRepository.findByProviderUserIdOrUserId(guid, guid).stream()
				.filter(su -> userRepository.findOne(su.getUserId())!=null)
				.findFirst();
	}
}