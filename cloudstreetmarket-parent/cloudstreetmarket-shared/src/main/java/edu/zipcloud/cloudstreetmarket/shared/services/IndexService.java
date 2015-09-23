package edu.zipcloud.cloudstreetmarket.shared.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;

import edu.zipcloud.cloudstreetmarket.core.entities.ChartIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

public interface IndexService{
	Page<Index> getIndices(String exchangeId, MarketId marketId, Pageable pageable);
	Page<Index> getIndices(Pageable pageable);
	Index getIndex(String id);

	ChartIndex getChartIndex(Index index, ChartType type,
			ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth,
			Integer intradayHeight);

}
