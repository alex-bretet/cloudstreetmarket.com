package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static edu.zipcloud.cloudstreetmarket.api.resources.MarketResource.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import edu.zipcloud.cloudstreetmarket.api.assemblers.MarketResourceAssembler;
import edu.zipcloud.cloudstreetmarket.api.resources.MarketResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.services.MarketService;

@Api(value = MARKETS, description = "Financial markets") // Swagger annotation
@RestController
@ExposesResourceFor(Market.class)
@RequestMapping(value=MARKETS_PATH, produces={"application/xml", "application/json"})
public class MarketController extends CloudstreetApiWCI {
	
	@Autowired
	private MarketService marketService;

	@Autowired
	private MarketResourceAssembler assembler;
	
    @Autowired
    private PagedResourcesAssembler<Market> pagedAssembler;
    
	@RequestMapping(method=GET)
	@ApiOperation(value = "Get list of markets", notes = "Return a page of markets")
	public PagedResources<MarketResource> getSeveral(
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"name"}, direction=Direction.ASC) Pageable pageable){
		return pagedAssembler.toResource(marketService.getAll(pageable), assembler);
	}
	
	@RequestMapping(value="/{market}", method=GET)
	@ApiOperation(value = "Get one markets", notes = "Return a market")
	public MarketResource get(
			@ApiParam(value="Market code: EUROPE") @PathVariable(value="market") MarketId marketId,
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"name"}, direction=Direction.ASC) Pageable pageable){
		return assembler.toResource(marketService.get(marketId));
	}
}