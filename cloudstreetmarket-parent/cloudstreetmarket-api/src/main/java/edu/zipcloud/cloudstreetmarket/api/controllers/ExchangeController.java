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

import static edu.zipcloud.cloudstreetmarket.api.resources.ExchangeResource.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import edu.zipcloud.cloudstreetmarket.api.assemblers.ExchangeResourceAssembler;
import edu.zipcloud.cloudstreetmarket.api.resources.ExchangeResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.services.ExchangeService;

@Api(value = EXCHANGES, description = "Trading places") // Swagger annotation
@RestController
@ExposesResourceFor(Exchange.class)
@RequestMapping(value=EXCHANGES_PATH, produces={"application/xml", "application/json"})
public class ExchangeController extends CloudstreetApiWCI<Exchange> {
	
	@Autowired
	private ExchangeService exchangeService;

	@Autowired
	private ExchangeResourceAssembler assembler;
	
	@RequestMapping(method=GET)
	@ApiOperation(value = "Get list of exchanges", notes = "Returns a page of exchanges")
	public PagedResources<ExchangeResource> getSeveral(
			@ApiParam(value="Market code: EUROPE") @RequestParam(value="market") MarketId marketId,
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"name"}, direction=Direction.ASC) Pageable pageable){
		return pagedAssembler.toResource(exchangeService.getSeveral(marketId, pageable), assembler);
	}
	
	@RequestMapping(value="/{exchange}", method=GET)
	@ApiOperation(value = "Get one exchange", notes = "Returns an exchange place")
	public ExchangeResource get(
			@ApiParam(value="Exchange id: LSE") @PathVariable(value="exchange") String exchangeId,
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"name"}, direction=Direction.ASC) Pageable pageable){
		return assembler.toResource(exchangeService.get(exchangeId));
	}
}