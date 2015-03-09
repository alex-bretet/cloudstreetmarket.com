package edu.zipcloud.cloudstreetmarket.core.services;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

import edu.zipcloud.cloudstreetmarket.core.daos.TransactionRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.UserRepository;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.Authority;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Action;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;

@Service(value="communityServiceImpl")
@Transactional(propagation = Propagation.REQUIRED)
public class CommunityServiceImpl implements ICommunityService {
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Page<UserActivityDTO> getPublicActivity(Pageable pageable) {
		List<UserActivityDTO> result = new LinkedList<UserActivityDTO>();
		Page<Transaction> transactions = transactionRepository.findAll(pageable);
		
		transactions.forEach(
			transaction -> result.add(
				new UserActivityDTO(
							transaction.getUser().getUsername(),
							transaction.getUser().getProfileImg(),
							transaction.getType(),
							transaction.getQuote().getStock().getCode(),
							transaction.getQuantity(),
							transaction.getType().equals(Action.BUY) ? 
									new BigDecimal(transaction.getQuote().getAsk()) 
										: new BigDecimal(transaction.getQuote().getBid()),
							transaction.getQuote().getDate()
				))
		);

		return new PageImpl<>(result, pageable, transactions.getTotalElements());
	}

	@Override
	public User findByUserName(String userName) {
		return userRepository.findOne(userName);
	}
	
	@Override
	public User createUser(User user, Role role) {
		if(findByUserName(user.getUsername()) != null){
			throw new ConstraintViolationException("The provided user name already exists!", null, null);
		}
		user.addAuthority(new Authority(user, role));
		return userRepository.save(user);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public User findOne(String username) {
		return userRepository.findOne(username);
	}

	@Override
	public void identifyUser(User user) {
		Preconditions.checkArgument(user.getPassword() != null, "The provided password cannot be null!");
		Preconditions.checkArgument(StringUtils.isNotEmpty(user.getPassword()), "The provided password cannot be empty!");
		
		User retreivedUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		if(retreivedUser == null){
			throw new BadCredentialsException("No match has been found with the provided credentials!");
		}
	}
}
