package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;

import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;

public interface HistoricalIndexRepository {
	
	Iterable<HistoricalIndex> findIntraDay(String code, Date of);
	Iterable<HistoricalIndex> findLastIntraDay(String code);
	HistoricalIndex findLastHistoric(String code);
	
}
