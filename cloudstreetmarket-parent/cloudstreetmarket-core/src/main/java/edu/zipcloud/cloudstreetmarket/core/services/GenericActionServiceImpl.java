package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.ActionRepository;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.AccountActivity;
import edu.zipcloud.cloudstreetmarket.core.entities.Action;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;

@Service
@Transactional(readOnly = true)
public class GenericActionServiceImpl implements GenericActionService {

	@Autowired
	private ActionRepository actionRepository;
	
	@Override
	public Action get(Long id) {
		return actionRepository.findOne(id);
	}

	@Override
	@Transactional
	public Action save(Action action) {
		return actionRepository.save(action);
	}

	@Override
	@Transactional
	public Action create(Action action) {
		return hydrate(actionRepository.save(action));
	}

	@Override
	public Action hydrate(Action action) {
		return action;
	}

	@Override
	@Transactional
	public void delete(Long actionId) {
		actionRepository.delete(actionId);
	}

	@Override
	public Page<UserActivityDTO> getPublicActivity(Pageable pageable) {
		Page<Action> actions = actionRepository.findAll(pageable);
		List<UserActivityDTO> result = new LinkedList<UserActivityDTO>();
		actions.getContent().forEach(
				a -> {
					if(a instanceof AccountActivity){
						UserActivityDTO accountActivity = new UserActivityDTO(
								a.getUser().getUsername(),
								a.getUser().getProfileImg(),
								((AccountActivity)a).getType(),
								((AccountActivity)a).getDate(),
								a.getId()
						);
						accountActivity.setSocialReport(a.getSocialEventAction());
						result.add(accountActivity);
					}
					else if(a instanceof Transaction){
						UserActivityDTO transaction = new UserActivityDTO((Transaction)a);
						transaction.setSocialReport(a.getSocialEventAction());
						result.add(transaction);
					}
				}
			);
		
		return new PageImpl<>(result, pageable, actions.getTotalElements());
	}
}
