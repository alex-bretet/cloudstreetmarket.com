package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.Date;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

public interface IndexService{
	Page<Index> getIndicesByMarket(MarketId marketId, Pageable pageable);
	Page<Index> getIndices(Pageable pageable);
	Set<HistoricalIndex> getHistoricalIndexData(String id, MarketId marketId, Date fromDate, Date toDate, QuotesInterval interval);
	Index getIndex(String id);
}
