package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.Action;

public interface GenericActionService extends ActionService<Action>{
	Page<UserActivityDTO> getPublicActivity(Pageable pageable);
}
