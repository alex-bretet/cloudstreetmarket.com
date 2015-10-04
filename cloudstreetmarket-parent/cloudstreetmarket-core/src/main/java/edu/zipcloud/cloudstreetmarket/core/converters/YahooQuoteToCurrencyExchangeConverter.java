/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
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