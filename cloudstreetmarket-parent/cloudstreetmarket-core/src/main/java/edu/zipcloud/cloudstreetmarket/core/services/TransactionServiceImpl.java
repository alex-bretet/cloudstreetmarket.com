/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockQuoteRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.TransactionRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.UserRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Quote;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

@Service
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StockQuoteRepository stockQuoteRepository;
	
	@Autowired
	private StockProductRepository stockProductRepository;
	
	@Override
	public Transaction get(Long transactionId) {
		return transactionRepository.findOne(transactionId);
	}

	@Override
	@Transactional
	public Transaction save(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	@Override
	@Transactional
	public Transaction create(Transaction transaction) {
		if(!transactionRepository.findByUserAndQuote(transaction.getUser(), transaction.getQuote()).isEmpty()){
			throw new DataIntegrityViolationException("A transaction for the quote and the user already exists!");
		}
		return transactionRepository.save(transaction);
	}

	@Override
	public Page<Transaction> findBy(Pageable pageable, String userName, Long quoteId, String ticker) {

		Quote quote = (quoteId != null) ? stockQuoteRepository.findOne(quoteId): null;
		User user = (userName != null) ? userRepository.findOne(userName) : null;
		
		//TODO to be turned generic
		StockProduct stockProduct = (ticker != null) ? stockProductRepository.findOne(ticker) : null;
		
		if(user == null){
			if(quote != null){
				return transactionRepository.findByQuote(pageable, quote);
			}
			else if(stockProduct != null){
				throw new IllegalArgumentException("Search operation unavailable, if a username isn't provided, a quoteId must be provided!!");
			}
			else{
				throw new IllegalArgumentException("If a username isn't provided, a quoteId must be provided!");
			}
		}
		else{
			if(quote != null){
				return transactionRepository.findByUserAndQuote(pageable, user, quote);
			}
			else if(stockProduct != null){
				//TODO to be turned generic
				return transactionRepository.findByUserAndProduct(pageable, user, stockProduct);
			}
			else{
				return transactionRepository.findByUser(pageable, user);
			}
		}
	}

	@Override
	public Transaction hydrate(final Transaction transaction) {
		
		if(transaction.getQuote().getId() != null){
			transaction.setQuote(stockQuoteRepository.findOne(transaction.getQuote().getId()));
		}
		
		if(transaction.getUser().getId() != null){
			transaction.setUser(userRepository.findOne(transaction.getUser().getId()));
		}
		
		if(transaction.getDate() == null){
			transaction.setDate(new Date());
		}
		
		return transaction;
	}

	@Override
	public boolean isOwnedByUser(User user, int quantity, StockProduct stock) {
		//TODO to be turned generic (multi-product)
		List<Transaction> previousTransactions = transactionRepository.findByUserAndProduct(user, stock);
		int balanceOfProducts = 0;
		
		for(Transaction transaction: previousTransactions){
			if(UserActivityType.BUY.equals(transaction.getType())){
				balanceOfProducts += transaction.getQuantity();
			}
			else if(UserActivityType.SELL.equals(transaction.getType())){
				balanceOfProducts -= transaction.getQuantity();
			}
		}

		return balanceOfProducts >= quantity;
	}

	@Override
	@Transactional
	public void delete(Long transactionId) {
		transactionRepository.delete(transactionId);
	}
}
