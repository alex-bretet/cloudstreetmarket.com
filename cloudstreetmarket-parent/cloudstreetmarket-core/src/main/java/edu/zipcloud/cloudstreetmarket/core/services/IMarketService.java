package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;

import edu.zipcloud.cloudstreetmarket.core.dtos.HistoProductDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.IndexOverviewDTO;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketCode;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

public interface IMarketService {
	@Secured({"ROLE_ANONYMOUS","ROLE_BASIC"})
	Page<IndexOverviewDTO> getLastDayIndicesOverview(MarketCode market, Pageable pageable);
	@Secured({"ROLE_ANONYMOUS","ROLE_BASIC"})
	Page<IndexOverviewDTO> getLastDayIndicesOverview(Pageable pageable);
	@Secured({"ROLE_ANONYMOUS","ROLE_BASIC"})
	HistoProductDTO getHistoIndex(String code, MarketCode market, Date fromDate, Date toDate, QuotesInterval interval);
}
