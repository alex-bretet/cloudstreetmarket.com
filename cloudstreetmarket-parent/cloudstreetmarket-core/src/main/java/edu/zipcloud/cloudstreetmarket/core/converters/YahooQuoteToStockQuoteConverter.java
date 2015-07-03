package edu.zipcloud.cloudstreetmarket.core.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.StockQuoteRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.services.ExchangeService;

@Component
@Transactional(readOnly=true)
public class YahooQuoteToStockQuoteConverter implements Converter<YahooQuote, StockQuote> {

	@Autowired
	private StockQuoteRepository stockQuoteRepository;
	
	@Autowired
	private ExchangeService exchangeService;
	
	@Override
	public StockQuote convert(YahooQuote yahooQuote) {
		
		StockQuote stockQuote = new StockQuote();

		stockQuote.setHigh(yahooQuote.getHigh());
		stockQuote.setLow(yahooQuote.getLow());
		stockQuote.setLastChange(yahooQuote.getLastChange());
		stockQuote.setLastChangePercent(yahooQuote.getLastChangePercent());
		stockQuote.setPreviousClose(yahooQuote.getPreviousClose());
		stockQuote.setOpen(yahooQuote.getOpen());
		stockQuote.setExchange(yahooQuote.getExchange());
		stockQuote.setCurrency(yahooQuote.getCurrency());
		
		return stockQuote;
	}
}