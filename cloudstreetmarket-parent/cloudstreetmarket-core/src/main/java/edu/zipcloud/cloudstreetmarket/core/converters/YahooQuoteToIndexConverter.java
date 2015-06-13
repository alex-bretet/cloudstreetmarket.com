package edu.zipcloud.cloudstreetmarket.core.converters;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.services.ExchangeService;

@Component
@Transactional(readOnly=true)
public class YahooQuoteToIndexConverter implements Converter<YahooQuote, Index> {

	@Autowired
	private ExchangeService exchangeService;
	
	@Autowired
	private IndexRepository indexRepository;
	
	@Override
	public Index convert(YahooQuote yahooQuote) {
		
		Index index = indexRepository.findOne(yahooQuote.getId());
		if(index == null){
			index = new Index();
			index.setId(yahooQuote.getId());
		}

		index.setName(yahooQuote.getName());
		index.setOpen(BigDecimal.valueOf(yahooQuote.getOpen()));
		index.setHigh(BigDecimal.valueOf(yahooQuote.getHigh()));
		index.setLow(BigDecimal.valueOf(yahooQuote.getLow()));
		index.setDailyLatestChange(BigDecimal.valueOf(yahooQuote.getLastChange()));
		index.setDailyLatestChangePercent(BigDecimal.valueOf(yahooQuote.getLastChangePercent()));
		index.setDailyLatestValue(BigDecimal.valueOf(yahooQuote.getLast()));
		index.setPreviousClose(BigDecimal.valueOf(yahooQuote.getPreviousClose()));
		index.setComponents(Sets.newHashSet());
		if(!StringUtils.isEmpty(yahooQuote.getExchange())){
			index.setExchange(exchangeService.get(yahooQuote.getExchange()));
		}
		return index;
	}
}