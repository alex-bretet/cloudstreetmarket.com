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