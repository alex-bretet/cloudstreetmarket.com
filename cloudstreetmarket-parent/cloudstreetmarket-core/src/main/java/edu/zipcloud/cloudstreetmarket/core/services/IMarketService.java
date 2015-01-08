package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.List;

import edu.zipcloud.cloudstreetmarket.core.dtos.DailyMarketActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.MarketOverviewDTO;

public interface IMarketService {
	DailyMarketActivityDTO getLastDayMarketActivity(String string);
	List<MarketOverviewDTO> getLastDayMarketsOverview();
}

