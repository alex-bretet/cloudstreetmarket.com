package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.zipcloud.cloudstreetmarket.core.entities.Quote;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

public interface TransactionRepositoryJpa extends JpaRepository<Transaction, Long>{
	
	List<Transaction> findAll();
	Page<Transaction> findByQuote(Pageable pageable, Quote user);
	Page<Transaction> findByUser(Pageable pageable, User user);
	Page<Transaction> findByUserAndQuote(Pageable pageable, User user, Quote quote);
	List<Transaction> findByUserAndQuote(User user, StockQuote quote);
	
}
