package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;

public interface TransactionRepositoryJpa extends JpaRepository<Transaction, Integer>{
	
	List<Transaction> findAll();
	
}
