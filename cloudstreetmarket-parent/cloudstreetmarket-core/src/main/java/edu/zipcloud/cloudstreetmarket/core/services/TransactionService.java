package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

public interface TransactionService extends ActionService<Transaction> {
	Page<Transaction> findBy(Pageable pageable, String userName, Long quoteId, String ticker);
	boolean isOwnedByUser(User user, int quantity, StockProduct stock);
}
