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
package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.Quote;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

public interface TransactionRepository {
	
	void delete(Long transactionId);
	Page<Transaction> findAll(Pageable pageable);
	Page<Transaction> findByUser(Pageable pageable, User user);
	List<Transaction> findByUser(User user);

	Page<Transaction> findByUserAndQuote(Pageable pageable, User user, Quote quote);
	Iterable<Transaction> findTransactions(Date from);
	Iterable<Transaction> findTransactions(int nb);
	Transaction findOne(Long transactionId);
	Transaction save(Transaction transaction);
	List<Transaction> findByUserAndQuote(User user, StockQuote quote);
	Page<Transaction> findByQuote(Pageable pageable, Quote quote);
	Page<Transaction> findByUserAndProduct(Pageable pageable, User user, StockProduct stockProduct);
	List<Transaction> findByUserAndProduct(User user, StockProduct product);
	List<String> findStockIdsByUser(User user);

}