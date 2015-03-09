package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;

@Service
public class DummyCommunityServiceImpl implements ICommunityService {
	
	@Override
	public Page<UserActivityDTO> getPublicActivity(Pageable pageable) {
		return null;
	}

	@Override
	public User findByUserName(String userName) {
		return null;
	}

	@Override
	public User createUser(User user, Role role) {
		return null;
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public User findOne(String username) {
		return null;
	}

	@Override
	public void identifyUser(User user) {
		return ;
	}
}
