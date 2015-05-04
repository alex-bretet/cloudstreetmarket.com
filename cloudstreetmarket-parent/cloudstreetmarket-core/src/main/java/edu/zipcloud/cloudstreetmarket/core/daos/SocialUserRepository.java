package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.List;
import java.util.Set;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.util.MultiValueMap;
import edu.zipcloud.cloudstreetmarket.core.entities.SocialUser;

public interface SocialUserRepository {
	
	List<SocialUser> findUsersConnectedTo(String providerId);
	List<String> findUserIdsByProviderIdAndProviderUserId(String providerId, String providerUserId);
	List<String> findUserIdsByProviderIdAndProviderUserIds(String providerId, Set<String> providerUserIds);
	
	SocialUser create(String userId, String providerId, String providerUserId,
			int rank, String displayName, String profileUrl, String imageUrl,
			String accessToken, String secret, String refreshToken,
			Long expireTime);
	SocialUser save(SocialUser user);
	
	void delete(String userId, String providerId, String providerUserId);
	void delete(String userId, String providerId);
	
	List<SocialUser> findAllByProviderIdAndProviderUserId(String providerId, String providerUserId) throws IncorrectResultSizeDataAccessException;
	
	SocialUser findFirstByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);
	List<SocialUser> findByProviderUserIdOrUserId(String providerUserId, String userId);
	
	List<SocialUser> findAllByUserIdAndProviderId(String userId, String providerId);
	List<SocialUser> findAllByUserId(String userId);
	List<SocialUser> findAllByUserIdAndProviderIdIn(String userId, MultiValueMap<String, String> providerUsers);
	
	Set<String> findUsersConnectedTo(String providerId, Set<String> providerUserIds);
	List<SocialUser> getPrimary(String userId, String providerId);
	int getRank(String userId, String providerId);
	
	SocialUser findFirstByUserIdAndProviderId(String userId, String providerId);
	
}