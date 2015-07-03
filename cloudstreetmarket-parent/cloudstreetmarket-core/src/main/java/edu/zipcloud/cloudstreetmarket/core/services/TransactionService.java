package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;

public interface TransactionService {
	Transaction get(Long transaction);
	Transaction save(Transaction transaction);
	Page<Transaction> findBy(Pageable pageable, String userName, Long quoteId);
	Transaction create(Transaction transaction);
}
