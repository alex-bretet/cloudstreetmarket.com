package edu.zipcloud.cloudstreetmarket.core.converters;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import edu.zipcloud.cloudstreetmarket.core.daos.CurrencyExchangeRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;
import edu.zipcloud.cloudstreetmarket.core.services.ExchangeService;

@Component
@Transactional(readOnly=true)
public class YahooQuoteToCurrencyExchangeConverter implements Converter<YahooQuote, CurrencyExchange> {

	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;
	
	@Autowired
	private ExchangeService exchangeService;
	
	@Override
	public CurrencyExchange convert(YahooQuote yahooQuote) {
		
		CurrencyExchange currencyExchange = currencyExchangeRepository.findOne(yahooQuote.getId());
		if(currencyExchange == null){
			currencyExchange = new CurrencyExchange();
			currencyExchange.setId(yahooQuote.getId());
		}
		
		currencyExchange.setName(yahooQuote.getName());
		currencyExchange.setBid(BigDecimal.valueOf(yahooQuote.getBid()));
		currencyExchange.setAsk(BigDecimal.valueOf(yahooQuote.getAsk()));
		currencyExchange.setDailyLatestChange(BigDecimal.valueOf(yahooQuote.getLastChange()));
		currencyExchange.setDailyLatestChangePercent(BigDecimal.valueOf(yahooQuote.getLastChangePercent()));
		currencyExchange.setDailyLatestValue(BigDecimal.valueOf(yahooQuote.getLast()));
		currencyExchange.setPreviousClose(BigDecimal.valueOf(yahooQuote.getPreviousClose()));
		currencyExchange.setOpen(BigDecimal.valueOf(yahooQuote.getOpen()));
		return currencyExchange;
	}
}