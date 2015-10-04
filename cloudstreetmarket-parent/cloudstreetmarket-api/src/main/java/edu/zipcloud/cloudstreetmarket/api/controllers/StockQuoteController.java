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
package edu.zipcloud.cloudstreetmarket.api.controllers;

import static edu.zipcloud.cloudstreetmarket.api.resources.StockQuoteResource.QUOTES_PATH;
import static edu.zipcloud.cloudstreetmarket.api.resources.StockQuoteResource.STOCK_QUOTES;
import static edu.zipcloud.cloudstreetmarket.api.resources.StockQuoteResource.STOCK_QUOTES_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import edu.zipcloud.cloudstreetmarket.api.assemblers.StockQuoteResourceAssembler;
import edu.zipcloud.cloudstreetmarket.api.resources.StockQuoteResource;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.services.StockQuoteService;

@Api(value = STOCK_QUOTES, description = "Stock Quotes") // Swagger annotation
@RestController
@ExposesResourceFor(StockQuote.class)
@RequestMapping(value=QUOTES_PATH + STOCK_QUOTES_PATH, produces={"application/xml", "application/json"})
public class StockQuoteController extends CloudstreetApiWCI<StockQuote> {
	
	@Autowired
	private StockQuoteService stockQuoteService;
	
	@Autowired
	private StockQuoteResourceAssembler assembler;
	
	@RequestMapping(value="/{id:[\\d]+}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one stock-overview", notes = "Return one stock-overview")
	public StockQuoteResource get(@ApiParam(value="StockQuote id: 24") @PathVariable(value="id") Long stockQuoteId){
		return assembler.toResource(stockQuoteService.get(stockQuoteId));
	}
}