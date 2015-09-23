package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.ActionRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.LikeActionRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.UserRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.LikeAction;

@Service
@Transactional(readOnly = true)
public class LikeActionServiceImpl implements LikeActionService {

	@Autowired
	private LikeActionRepository likeActionRepository;
	
	@Autowired
	private ActionRepository actionRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public LikeAction get(Long id) {
		return likeActionRepository.findOne(id);
	}

	@Override
	@Transactional
	public LikeAction save(LikeAction action) {
		return likeActionRepository.save(action);
	}

	@Override
	@Transactional
	public LikeAction create(LikeAction action) {
		return hydrate(likeActionRepository.save(action));
	}

	@Override
	public LikeAction hydrate(LikeAction action) {
		action.setUser(userRepository.findOne(action.getUser().getId()));
		return action;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void delete(Long actionId) {
		likeActionRepository.delete(actionId);
	}

	@Override
	public Page<LikeAction> findBy(Pageable pageable, Long actionId) {
		return likeActionRepository.findByTargetAction(pageable, get(actionId));
	}
}
