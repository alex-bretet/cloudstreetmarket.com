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