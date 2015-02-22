package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.dtos.HistoProductDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.IndexOverviewDTO;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketCode;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

@Service
public class DummyMarketServiceImpl implements IMarketService {

	@Override
	public Page<IndexOverviewDTO> getLastDayIndicesOverview(MarketCode market,
			Pageable pageable) {
		return null;
	}

	@Override
	public Page<IndexOverviewDTO> getLastDayIndicesOverview(Pageable pageable) {
		return null;
	}

	@Override
	public HistoProductDTO getHistoIndex(String code, MarketCode market,
			Date fromDate, Date toDate, QuotesInterval interval) {
		return null;
	}

}
