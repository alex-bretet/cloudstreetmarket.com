package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
}
