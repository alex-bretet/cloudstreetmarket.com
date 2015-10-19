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