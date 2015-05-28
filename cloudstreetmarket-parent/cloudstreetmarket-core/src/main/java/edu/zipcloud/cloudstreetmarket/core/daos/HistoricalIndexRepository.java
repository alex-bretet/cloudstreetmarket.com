package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;
import java.util.List;

import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

public interface HistoricalIndexRepository {
	
	List<HistoricalIndex> findSelection(String code, MarketId marketCode, Date fromDate, Date toDate, QuotesInterval interval);
	List<HistoricalIndex> findIntraDay(String code, Date of);
	List<HistoricalIndex> findOnLastIntraDay(String code);
	HistoricalIndex findLast(String code);

}
