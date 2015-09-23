package edu.zipcloud.cloudstreetmarket.shared.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;

import edu.zipcloud.cloudstreetmarket.core.entities.ChartStock;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.services.ProductService;

public interface StockProductService extends ProductService<StockProduct> {
	Page<StockProduct> get(String indexId, String exchangeId, MarketId marketId, String startWith, Specification<StockProduct> spec, Pageable pageable, boolean validResults);
	StockProduct get(String stockProductId);
	ChartStock getChartStock(StockProduct index, ChartType type,
			ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth,
			Integer intradayHeight);
}
