package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;
import java.util.List;

import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

public interface HistoricalIndexRepository {
	
	List<HistoricalIndex> findSelection(String id, Date fromDate, Date toDate, QuotesInterval interval);
	List<HistoricalIndex> findIntraDay(String id, Date of);
	List<HistoricalIndex> findOnLastIntraDay(String id);
	HistoricalIndex findLast(String code);

}
