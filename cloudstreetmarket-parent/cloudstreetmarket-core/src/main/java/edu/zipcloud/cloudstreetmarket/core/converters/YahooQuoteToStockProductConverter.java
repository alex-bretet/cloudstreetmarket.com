package edu.zipcloud.cloudstreetmarket.core.converters;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.services.ExchangeService;

@Component
@Transactional(readOnly=true)
public class YahooQuoteToStockProductConverter implements Converter<YahooQuote, StockProduct> {

	@Autowired
	private StockProductRepository stockProductRepository;
	
	@Autowired
	private ExchangeService exchangeService;
	
	@Override
	public StockProduct convert(YahooQuote yahooQuote) {
		
		StockProduct stockProduct = stockProductRepository.findOne(yahooQuote.getId());
		if(stockProduct == null){
			stockProduct = new StockProduct();
			stockProduct.setId(yahooQuote.getId());
		}
		
		stockProduct.setName(yahooQuote.getName());
		stockProduct.setHigh(BigDecimal.valueOf(yahooQuote.getHigh()));
		stockProduct.setLow(BigDecimal.valueOf(yahooQuote.getLow()));
		stockProduct.setDailyLatestChange(BigDecimal.valueOf(yahooQuote.getLastChange()));
		stockProduct.setDailyLatestChangePercent(BigDecimal.valueOf(yahooQuote.getLastChangePercent()));
		stockProduct.setDailyLatestValue(BigDecimal.valueOf(yahooQuote.getLast()));
		stockProduct.setPreviousClose(BigDecimal.valueOf(yahooQuote.getPreviousClose()));
		stockProduct.setOpen(BigDecimal.valueOf(yahooQuote.getOpen()));
		if(!StringUtils.isEmpty(yahooQuote.getExchange())){
			stockProduct.setExchange(exchangeService.get(yahooQuote.getExchange()));
		}
		if(!StringUtils.isEmpty(yahooQuote.getCurrency())){
			stockProduct.setCurrency(yahooQuote.getCurrency());
		}
		
		return stockProduct;
	}
}