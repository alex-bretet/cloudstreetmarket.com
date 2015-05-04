package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.social.connect.UsersConnectionRepository;

import edu.zipcloud.cloudstreetmarket.core.entities.SocialUser;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

public interface SocialUserService extends UsersConnectionRepository {
	SocialUser bindSocialUserToUser(String guid, User user, String provider);
	SocialUser getRegisteredSocialUser(String guid);
	boolean isSocialUserAlreadyRegistered(String guid);
}