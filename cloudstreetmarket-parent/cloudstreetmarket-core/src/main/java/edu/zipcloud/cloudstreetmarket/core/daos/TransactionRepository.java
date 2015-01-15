package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;

import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

public interface TransactionRepository {
	
	Iterable<Transaction> findAll();
	Iterable<Transaction> findByUser(User user);
	Iterable<Transaction> findRecentTransactions(Date from);
	Iterable<Transaction> findRecentTransactions(int nb);

}
