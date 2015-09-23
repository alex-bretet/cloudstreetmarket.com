package edu.zipcloud.cloudstreetmarket.api.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;

import edu.zipcloud.cloudstreetmarket.core.entities.ChartStock;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.shared.services.StockProductService;

public interface StockProductServiceOnline extends StockProductService {
	Page<StockProduct> gather(String indexId, String exchangeId, MarketId marketId, String startWith, Specification<StockProduct> spec, Pageable pageable);
	StockProduct gather(String stockProductId);
	ChartStock gather(String indexId, ChartType type, ChartHistoSize histoSize,
			ChartHistoMovingAverage histoAverage, ChartHistoTimeSpan histoPeriod, 
			Integer intradayWidth, Integer intradayHeight) throws ResourceNotFoundException;
	List<StockProduct> gather(String[] stockProductId);
	Map<String, StockProduct> gatherAsMap(String[] stockProductId);
}
