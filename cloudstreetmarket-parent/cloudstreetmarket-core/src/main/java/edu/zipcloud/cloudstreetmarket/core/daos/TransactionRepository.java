package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

public interface TransactionRepository {
	
	Page<Transaction> findAll(Pageable pageable);
	Iterable<Transaction> findByUser(User user);
	Iterable<Transaction> findTransactions(Date from);
	Iterable<Transaction> findTransactions(int nb);

}
