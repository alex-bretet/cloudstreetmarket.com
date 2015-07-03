package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.Quote;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

public interface TransactionRepository {
	
	Page<Transaction> findAll(Pageable pageable);
	Page<Transaction> findByUser(Pageable pageable, User user);
	Page<Transaction> findByUserAndQuote(Pageable pageable, User user, Quote quote);
	Iterable<Transaction> findTransactions(Date from);
	Iterable<Transaction> findTransactions(int nb);
	Transaction findOne(Long transactionId);
	Transaction save(Transaction transaction);
	List<Transaction> findByUserAndQuote(User user, StockQuote quote);
	Page<Transaction> findByQuote(Pageable pageable, Quote quote);
}
