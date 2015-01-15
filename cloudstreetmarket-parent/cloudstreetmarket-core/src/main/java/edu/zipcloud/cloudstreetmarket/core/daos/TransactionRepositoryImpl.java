package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository{

	@PersistenceContext 
	private EntityManager em;
	
	@Autowired
	private TransactionRepositoryJpa repo;

	@Override
	public Iterable<Transaction> findByUser(User user) {
		TypedQuery<Transaction> sqlQuery = em.createQuery("from Transaction where user = ?", Transaction.class);
		return sqlQuery.setParameter(1, user).getResultList();
	}

	@Override
	public Iterable<Transaction> findRecentTransactions(Date from) {
		TypedQuery<Transaction> sqlQuery = em.createQuery("from Transaction t where t.quote.date >= ?", Transaction.class);
		return sqlQuery.setParameter(1, from).getResultList();
	}

	@Override
	public Iterable<Transaction> findRecentTransactions(int nb) {
		TypedQuery<Transaction> sqlQuery = em.createQuery("from Transaction t ORDER BY t.quote.date desc", Transaction.class);
		return sqlQuery.setMaxResults(nb).getResultList();
	}

	@Override
	public Iterable<Transaction> findAll() {
		return repo.findAll();
	}
}
