package edu.zipcloud.cloudstreetmarket.api.services;

import java.util.Date;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

public interface IndexService{
	Page<Index> getIndices(String exchangeId, MarketId marketId, Pageable pageable);
	Page<Index> getIndices(Pageable pageable);
	Set<HistoricalIndex> gatherHisto(String id, Date fromDate, Date toDate, QuotesInterval interval);
	Page<Index> gather(String exchangeId, MarketId marketId, Pageable pageable);
	Index gather(String indexId);
	Index getIndex(String id);
}
