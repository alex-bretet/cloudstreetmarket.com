package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

public interface TransactionService {
	Transaction get(Long transaction);
	Transaction save(Transaction transaction);
	Page<Transaction> findBy(Pageable pageable, String userName, Long quoteId, String ticker);
	Transaction create(Transaction transaction);
	Transaction hydrate(Transaction transaction);
	boolean isOwnedByUser(User user, int quantity, StockProduct stock);
}
