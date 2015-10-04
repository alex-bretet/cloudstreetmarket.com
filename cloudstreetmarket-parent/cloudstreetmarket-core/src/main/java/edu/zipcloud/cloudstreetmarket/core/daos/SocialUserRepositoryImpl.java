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

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import edu.zipcloud.cloudstreetmarket.core.entities.SocialUser;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class SocialUserRepositoryImpl implements SocialUserRepository, Serializable{

	private static final long serialVersionUID = -1094865428182160411L;

	@PersistenceContext 
	private EntityManager em;
	
	@Autowired
	private SocialUserRepositoryJpa repo;

	@Override
	public SocialUser save(SocialUser user) {
		return repo.save(user);
	}

	@Override
	public List<String> findUserIdsByProviderIdAndProviderUserId(
			String providerId, String providerUserId) {
		List<SocialUser> socialUsers = repo.findAllByProviderIdAndProviderUserId(providerId, providerUserId);
		return socialUsers.stream()
				.map(SocialUser::getUserId)
				.collect(Collectors.toList());
	}

	@Override
	public List<String> findUserIdsByProviderIdAndProviderUserIds(
			String providerId, Set<String> providerUserIds) {
		Set<SocialUser> socialUsers = repo.findAllByProviderIdAndProviderUserIdIn(providerId, providerUserIds);
		return socialUsers.stream()
				.map(SocialUser::getUserId)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<SocialUser> findUsersConnectedTo(String providerId) {
		List<SocialUser> socialUsers = repo.findAllByProviderId(providerId);
		return socialUsers.stream()
				.collect(Collectors.toList());
	}
	
	@Override
	public Set<String> findUsersConnectedTo(String providerId, Set<String> providerUserIds) {
		Set<SocialUser> socialUsers = repo.findAllByProviderIdAndProviderUserIdIn(providerId, providerUserIds);
		return socialUsers.stream()
				.map(SocialUser::getUserId)
				.collect(Collectors.toSet());
	}

	@Override
	public List<SocialUser> getPrimary(String userId, String providerId) {
		List<SocialUser> socialUsers = repo.findAllByUserIdAndProviderId(userId, providerId);
		//TODO identify primary
		return socialUsers.stream()
				.collect(Collectors.toList());
	}

	@Override
	public int getRank(String userId, String providerId) {
		SocialUser socialUser = repo.findFirstByUserIdAndProviderId(userId, providerId);
		return socialUser!= null ? socialUser.getRank() : 0;
	}

	@Override
	public List<SocialUser> findAllByUserIdAndProviderIdIn(String userId,
			MultiValueMap<String, String> providerUsers) {
		List<SocialUser> socialUsers = repo.findAllByUserIdAndProviderIdIn(userId, providerUsers.keySet());
		return socialUsers.stream()
				.filter(u -> u.getProviderUserId().equals(providerUsers.get(u.getProviderId())))
				.collect(Collectors.toList());
	}

	@Override
	public List<SocialUser> findAllByUserId(String userId) {
		return repo.findAllByUserId(userId);
	}

	@Override
	public List<SocialUser> findAllByUserIdAndProviderId(String userId, String providerId) {
		return repo.findAllByUserIdAndProviderId(userId, providerId);
	}

	@Override
	public SocialUser findFirstByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId) {
		return repo.findFirstByUserIdAndProviderIdAndProviderUserId(userId, providerId, providerUserId);
	}

	@Override
	public List<SocialUser> findAllByProviderIdAndProviderUserId(String providerId, String providerUserId)
			throws IncorrectResultSizeDataAccessException {
		List<SocialUser> socialUsers = repo.findAllByProviderIdAndProviderUserId(providerId, providerUserId);
		if(socialUsers == null){
			return null;
		}
		if(socialUsers.size() > 1){
			throw new IncorrectResultSizeDataAccessException("get(String providerId, String providerUserId) brought back:", socialUsers.size());
		}
		return socialUsers;
	}

	@Override
	public void delete(String userId, String providerId) {
		repo.delete(repo.findAllByUserIdAndProviderId(userId, providerId));
	}

	@Override
	public void delete(String userId, String providerId, String providerUserId) {
		repo.delete(repo.findFirstByUserIdAndProviderIdAndProviderUserId(userId, providerId, providerUserId));
	}

	@Override
	public SocialUser create(String userId, String providerId,
			String providerUserId, int rank, String displayName,
			String profileUrl, String imageUrl, String accessToken,
			String secret, String refreshToken, Long expireTime) {
		
		SocialUser user = new SocialUser();
		user.setUserId(userId);
		user.setProviderId(providerId);
		user.setProviderUserId(providerUserId);
		user.setRank(rank);
		user.setDisplayName(displayName);
		user.setProfileUrl(profileUrl);
		user.setImageUrl(imageUrl);
		user.setAccessToken(accessToken);
		user.setSecret(secret);
		user.setRefreshToken(refreshToken);
		user.setExpireTime(expireTime);
		
		return repo.save(user);
	}
	
	@Override
	public List<SocialUser> findByProviderUserIdOrUserId(String providerUserId, String userId) {
		return repo.findByProviderUserIdOrUserId(providerUserId, userId);
	}
	
	@Override
	public SocialUser findFirstByUserIdAndProviderId(String userId, String providerId) {
		return repo.findFirstByUserIdAndProviderId(userId, providerId);
	}
}