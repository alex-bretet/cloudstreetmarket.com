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
package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockQuoteRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;

@Service
@Transactional(readOnly = true)
public class StockQuoteServiceImpl implements StockQuoteService {

	@Autowired
	private StockQuoteRepository stockQuoteRepository;
	
	@Autowired
	private StockProductRepository stockProductRepository;
	
	@Override
	public StockQuote get(Long stockQuoteId) {
		return stockQuoteRepository.findOne(stockQuoteId);
	}
	
	@Override
	public StockQuote hydrate(final StockQuote stockQuote) {
		
		if(stockQuote.getStock().getId() != null){
			stockQuote.setStock(stockProductRepository.findOne(stockQuote.getStock().getId()));
		}

		return stockQuote;
	}
}
