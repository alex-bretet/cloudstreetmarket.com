package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import edu.zipcloud.cloudstreetmarket.core.entities.Quote;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

@Repository
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
		TypedQuery<Transaction> sqlQuery = em.createQuery("from Transaction t where t.quote.date >= ?", Transaction.class);
		return sqlQuery.setParameter(1, from).getResultList();
	}

	@Override
	public Iterable<Transaction> findTransactions(int nb) {
		TypedQuery<Transaction> sqlQuery = em.createQuery("from Transaction t ORDER BY t.quote.date desc", Transaction.class);
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
}
