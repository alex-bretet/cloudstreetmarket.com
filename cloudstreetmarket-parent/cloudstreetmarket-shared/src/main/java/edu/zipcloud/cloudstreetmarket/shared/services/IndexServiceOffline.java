package edu.zipcloud.cloudstreetmarket.shared.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;

import edu.zipcloud.cloudstreetmarket.core.entities.ChartIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

public interface IndexServiceOffline extends IndexService{

	Page<Index> gather(String userId, String exchangeId, MarketId marketId,
			Pageable pageable);
	Index gather(String userId, String indexId);
	
	ChartIndex gather(String userId, String indexId, ChartType type, ChartHistoSize histoSize,
			ChartHistoMovingAverage histoAverage, ChartHistoTimeSpan histoPeriod, 
			Integer intradayWidth, Integer intradayHeight) throws ResourceNotFoundException;

}
