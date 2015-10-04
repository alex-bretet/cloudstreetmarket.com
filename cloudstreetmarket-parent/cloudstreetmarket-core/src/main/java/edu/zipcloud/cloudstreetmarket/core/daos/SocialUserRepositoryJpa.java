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
package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.zipcloud.cloudstreetmarket.core.entities.SocialUser;

public interface SocialUserRepositoryJpa extends JpaRepository<SocialUser, String>{

	Set<SocialUser> findAllByProviderIdAndProviderUserIdIn(String providerId, Set<String> providerUserIds);
	List<SocialUser> findAllByUserIdAndProviderId(String userId, String providerId);
	List<SocialUser> findAllByUserIdAndProviderIdIn(String userId, Set<String> keySet);
	List<SocialUser> findAllByUserId(String userId);
	SocialUser findFirstByUserIdAndProviderId(String userId,String providerId);
	SocialUser findFirstByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);
	List<SocialUser> findAllByProviderIdAndProviderUserId(String providerId,String providerUserId);
	List<SocialUser> findAllByProviderId(String providerId);
	List<SocialUser> findByProviderUserIdOrUserId(String providerUserId, String userId);
}