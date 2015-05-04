package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.Authority;
import edu.zipcloud.cloudstreetmarket.core.entities.SocialUser;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;

public interface CommunityService extends UserDetailsService{
	Page<UserActivityDTO> getPublicActivity(Pageable pageable);
	User findByUserName(String userName);
	User findByLogin(String userName);
	
	Page<User> findAll(Pageable pageable);
	User createUser(User user, Role role);
	User createUser(User user, Role[] role);
	User createUser(String nickName, String email, String password);
	User findOne(String username);
	User identifyUser(User user);
	String generatePassword();
	void delete(String userName);
	Set<Authority> createAuthorities(Role[] roles);
	
	User getUserByEmail(String email);
	User save(User user);
	void registerUser(User user);
	Authentication signInUser(User user);
}
