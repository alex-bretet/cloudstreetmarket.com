package edu.zipcloud.cloudstreetmarket.core.converters;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

@Component
public class YahooQuoteToStockProductConverter implements Converter<YahooQuote, StockProduct> {

	@Override
	public StockProduct convert(YahooQuote yahooQuote) {
		StockProduct stockProduct = new StockProduct();
		stockProduct.setId(yahooQuote.getId());
		stockProduct.setName(yahooQuote.getName());
		stockProduct.setHigh(BigDecimal.valueOf(yahooQuote.getHigh()));
		stockProduct.setLow(BigDecimal.valueOf(yahooQuote.getLow()));
		stockProduct.setDailyLatestChange(BigDecimal.valueOf(yahooQuote.getLastChange()));
		stockProduct.setDailyLatestChangePercent(BigDecimal.valueOf(yahooQuote.getLastChangePercent()));
		stockProduct.setDailyLatestValue(BigDecimal.valueOf(yahooQuote.getLast()));
		stockProduct.setPreviousClose(BigDecimal.valueOf(yahooQuote.getPreviousClose()));
		return stockProduct;
	}
}