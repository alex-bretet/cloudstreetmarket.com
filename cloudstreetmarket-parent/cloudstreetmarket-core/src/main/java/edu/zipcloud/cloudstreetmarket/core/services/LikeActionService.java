package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.LikeAction;

public interface LikeActionService extends ActionService<LikeAction> {
	Page<LikeAction> findBy(Pageable pageable, Long actionId);
}
