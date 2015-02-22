package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;

@Service
public class DummyCommunityServiceImpl implements ICommunityService {
	
	@Override
	public Page<UserActivityDTO> getPublicActivity(Pageable pageable) {
		return null;
	}

}
