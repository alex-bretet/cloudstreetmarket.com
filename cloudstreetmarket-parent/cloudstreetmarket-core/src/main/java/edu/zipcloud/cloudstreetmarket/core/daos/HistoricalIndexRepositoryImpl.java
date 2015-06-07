package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;
import edu.zipcloud.core.util.DateUtil;

@Repository
public class HistoricalIndexRepositoryImpl implements HistoricalIndexRepository{

	@PersistenceContext 
	private EntityManager em;

	@Override
	public List<HistoricalIndex> findIntraDay(String id, Date of) {
		TypedQuery<HistoricalIndex> sqlQuery = em.createQuery("from HistoricalIndex h where h.index.id = ? and h.fromDate >= ? and h.toDate <= ? ORDER BY h.toDate asc", HistoricalIndex.class);
		sqlQuery.setParameter(1, id);
		sqlQuery.setParameter(2, DateUtil.getStartOfDay(of));
		sqlQuery.setParameter(3, DateUtil.getEndOfDay(of));
		return sqlQuery.getResultList();
	}
	
	@Override
	public List<HistoricalIndex> findSelection(String id, Date fromDate, Date toDate, QuotesInterval interval) {
		TypedQuery<HistoricalIndex> sqlQuery = em.createQuery("from HistoricalIndex h where h.index.id = ? and h.fromDate >= ? and h.toDate <= ? and h.interval = ? ORDER BY h.toDate asc", HistoricalIndex.class);
		sqlQuery.setParameter(1, id);
		sqlQuery.setParameter(2, fromDate);
		sqlQuery.setParameter(3, toDate);
		sqlQuery.setParameter(4, interval);
		return sqlQuery.getResultList();
	}
	
	@Override
	public List<HistoricalIndex> findOnLastIntraDay(String id) {
		HistoricalIndex lastValue = findLast(id);
		if(lastValue==null){
			return new ArrayList<>();
		}
		return findIntraDay(id, lastValue.getToDate());
	}

	@Override
	public HistoricalIndex findLast(String id){
		TypedQuery<HistoricalIndex> sqlQuery = em.createQuery("from HistoricalIndex h where h.index.id = ? ORDER BY h.toDate desc", HistoricalIndex.class);
		sqlQuery.setParameter(1, id);
		try{
			return sqlQuery.setMaxResults(1).getSingleResult();
		}
		catch(NoResultException e){
			return null;
		}
	}
}
