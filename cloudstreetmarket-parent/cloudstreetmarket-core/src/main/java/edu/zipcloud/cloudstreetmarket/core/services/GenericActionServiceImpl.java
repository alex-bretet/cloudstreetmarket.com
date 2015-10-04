/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.zipcloud.cloudstreetmarket.core.daos.ActionRepository;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.AccountActivity;
import edu.zipcloud.cloudstreetmarket.core.entities.Action;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

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
		Page<Action> actions = actionRepository.findAllByTypeIn(Lists.newArrayList(UserActivityType.REGISTER,UserActivityType.BUY,UserActivityType.SELL), pageable);
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
