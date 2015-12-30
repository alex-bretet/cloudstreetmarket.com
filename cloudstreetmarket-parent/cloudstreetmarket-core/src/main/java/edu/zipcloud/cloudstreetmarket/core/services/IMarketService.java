package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.zipcloud.cloudstreetmarket.core.dtos.HistoProductDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.IndexOverviewDTO;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketCode;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

public interface IMarketService {
	Page<IndexOverviewDTO> getLastDayIndicesOverview(MarketCode market, Pageable pageable);
	Page<IndexOverviewDTO> getLastDayIndicesOverview(Pageable pageable);
	HistoProductDTO getHistoIndex(String code, MarketCode market, Date fromDate, Date toDate, QuotesInterval interval);
}
