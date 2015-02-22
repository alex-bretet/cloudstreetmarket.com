package edu.zipcloud.cloudstreetmarket.core.services;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.daos.TransactionRepository;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.enums.Action;

@Service(value="communityServiceImpl")
public class CommunityServiceImpl implements ICommunityService {
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Page<UserActivityDTO> getPublicActivity(Pageable pageable) {
		List<UserActivityDTO> result = new LinkedList<UserActivityDTO>();
		Page<Transaction> transactions = transactionRepository.findAll(pageable);
		
		transactions.forEach(
			transaction -> result.add(
				new UserActivityDTO(
							transaction.getUser().getLoginName(),
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
}
