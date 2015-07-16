package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.entities.Quote;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

@Repository
@Transactional(readOnly = true)
public class TransactionRepositoryImpl implements TransactionRepository{

	@PersistenceContext 
	private EntityManager em;
	
	@Autowired
	private TransactionRepositoryJpa repo;

	@Override
	public Page<Transaction> findByUser(Pageable pageable, User user) {
		return repo.findByUser(pageable, user);
	}

	@Override
	public Iterable<Transaction> findTransactions(Date from) {
		TypedQuery<Transaction> sqlQuery = em.createNamedQuery(Transaction.FIND_FROM_QUOTE_DATE, Transaction.class);
		return sqlQuery.setParameter("quoteDate", from).getResultList();
	}

	@Override
	public Iterable<Transaction> findTransactions(int nb) {
		TypedQuery<Transaction> sqlQuery = em.createNamedQuery(Transaction.GET_ALL_ORDER_BY, Transaction.class);
		return sqlQuery.setMaxResults(nb).getResultList();
	}

	@Override
	public Page<Transaction> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public Transaction findOne(Long transactionId) {
		return repo.findOne(transactionId);
	}
	
	@Override
	@Transactional
	public Transaction save(Transaction transaction) {
		if(!repo.findByUserAndQuote(transaction.getUser(), transaction.getQuote()).isEmpty()){
			throw new DataIntegrityViolationException("A transaction for the quote and the user already exists!");
		}
		return repo.save(transaction);
	}

	@Override
	public Page<Transaction> findByUserAndQuote(Pageable pageable, User user, Quote quote) {
		return repo.findByUserAndQuote(pageable, user, quote);
	}

	@Override
	public List<Transaction> findByUserAndQuote(User user, StockQuote quote) {
		return repo.findByUserAndQuote(user, quote);
	}

	@Override
	public Page<Transaction> findByQuote(Pageable pageable, Quote quote) {
		return repo.findByQuote(pageable, quote);
	}
	
	@Override
	public List<Transaction> findByUserAndProduct(User user, StockProduct stockProduct) {
		TypedQuery<Transaction> sqlQuery = em.createNamedQuery(Transaction.FIND_BY_USER_AND_STOCK, Transaction.class);
		sqlQuery.setParameter("user", user);
		sqlQuery.setParameter("productId", stockProduct.getId());
		return sqlQuery.getResultList();
	}

	@Override
	public Page<Transaction> findByUserAndProduct(Pageable pageable, User user,
			StockProduct stockProduct) {
		
		TypedQuery<Transaction> sqlQuery = em.createNamedQuery(Transaction.FIND_BY_USER_AND_STOCK, Transaction.class);
		sqlQuery.setParameter(1,user);
		sqlQuery.setParameter(2, stockProduct.getId());
		sqlQuery.setFirstResult(pageable.getOffset());
		sqlQuery.setMaxResults(pageable.getPageSize());
		
		Query countQuery = em.createNamedQuery(Transaction.COUNT_BY_USER_AND_STOCK);
		countQuery.setParameter(1,user);
		countQuery.setParameter(2, stockProduct.getId());
		Long total=(Long) countQuery.getSingleResult();

		return new PageImpl<Transaction>(sqlQuery.getResultList(), pageable, total);
	}

	@Override
	public List<Transaction> findByUser(User user) {
		return repo.findByUser(user);
	}

	@Override
	public List<String> findStockIdsByUser(User user) {
		TypedQuery<String> sqlQuery = em.createNamedQuery(Transaction.FIND_STOCK_ID_BY_USER, String.class);
		sqlQuery.setParameter("user", user);
		return sqlQuery.getResultList();
	}
}
