package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;

public interface ICommunityService {
	Page<UserActivityDTO> getPublicActivity(Pageable pageable);
	User findByUserName(String userName);
	Page<User> findAll(Pageable pageable);
	User createUser(User user, Role role);
	User findOne(String username);
	void identifyUser(User user);
}
