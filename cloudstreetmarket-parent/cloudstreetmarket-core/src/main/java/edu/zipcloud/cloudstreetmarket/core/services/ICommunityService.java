package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.List;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;

public interface ICommunityService {
	List<UserActivityDTO> getLastUserPublicActivity(int number);
}
