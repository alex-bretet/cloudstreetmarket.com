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