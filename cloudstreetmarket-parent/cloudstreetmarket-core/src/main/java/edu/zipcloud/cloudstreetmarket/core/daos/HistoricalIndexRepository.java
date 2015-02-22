package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;

import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketCode;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

public interface HistoricalIndexRepository {
	
	Iterable<HistoricalIndex> findHistoric(String code, MarketCode market, Date fromDate, Date toDate, QuotesInterval interval);
	Iterable<HistoricalIndex> findIntraDay(String code, Date of);
	Iterable<HistoricalIndex> findLastIntraDay(String code);
	HistoricalIndex findLastHistoric(String code);

}
