package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketCode;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;
import edu.zipcloud.core.util.DateUtil;

@Repository
public class HistoricalIndexRepositoryImpl implements HistoricalIndexRepository{

	@PersistenceContext 
	private EntityManager em;

	@Override
	public Iterable<HistoricalIndex> findIntraDay(String code, Date of) {
		TypedQuery<HistoricalIndex> sqlQuery = em.createQuery("from HistoricalIndex h where h.index.code = ? and h.fromDate >= ? and h.toDate <= ? ORDER BY h.toDate asc", HistoricalIndex.class);
		sqlQuery.setParameter(1, code);
		sqlQuery.setParameter(2, DateUtil.getStartOfDay(of));
		sqlQuery.setParameter(3, DateUtil.getEndOfDay(of));
		return sqlQuery.getResultList();
	}
	
	@Override
	public Iterable<HistoricalIndex> findHistoric(String code, MarketCode market, Date fromDate, Date toDate, QuotesInterval interval) {
		TypedQuery<HistoricalIndex> sqlQuery = em.createQuery("from HistoricalIndex h where h.index.code = ? and h.index.market.code = ? and h.fromDate >= ? and h.toDate <= ? and h.interval = ? ORDER BY h.toDate asc", HistoricalIndex.class);
		sqlQuery.setParameter(1, code);
		sqlQuery.setParameter(2, market);
		sqlQuery.setParameter(3, fromDate);
		sqlQuery.setParameter(4, toDate);
		sqlQuery.setParameter(5, interval);
		return sqlQuery.getResultList();
	}
	
	@Override
	public Iterable<HistoricalIndex> findLastIntraDay(String code) {
		return findIntraDay(code, findLastHistoric(code).getToDate());
	}

	@Override
	public HistoricalIndex findLastHistoric(String code){
		TypedQuery<HistoricalIndex> sqlQuery = em.createQuery("from HistoricalIndex h where h.index.code = ? ORDER BY h.toDate desc", HistoricalIndex.class);
		sqlQuery.setParameter(1, code);
		return sqlQuery.setMaxResults(1).getSingleResult();
	}
}
