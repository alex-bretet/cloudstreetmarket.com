package edu.zipcloud.cloudstreetmarket.api.controllers;

import static edu.zipcloud.cloudstreetmarket.api.resources.IndexResource.*;
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

import edu.zipcloud.cloudstreetmarket.api.assemblers.IndexResourceAssembler;
import edu.zipcloud.cloudstreetmarket.api.resources.IndexResource;
import edu.zipcloud.cloudstreetmarket.api.services.IndexService;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

@Api(value = INDICES, description = "Financial indices") // Swagger annotation
@RestController
@ExposesResourceFor(Index.class)
@RequestMapping(value=INDICES_PATH, produces={"application/xml", "application/json"})
public class IndexController extends CloudstreetApiWCI<Index> {
	
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private IndexResourceAssembler assembler;
	
	@RequestMapping(method=GET)
	@ApiOperation(value = "Get overviews of indices", notes = "Return a page of index-overviews")
	public PagedResources<IndexResource> getSeveral(
			@RequestParam(value="exchange", required=false) String exchangeId,
			@RequestParam(value="market", required=false) MarketId marketId,
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"previousClose"}, direction=Direction.DESC) Pageable pageable){
		return pagedAssembler.toResource(indexService.gather(exchangeId, marketId, pageable), assembler);
	}

	@RequestMapping(value="/{index:[a-zA-Z0-9^.-:]+}{extension:\\.[a-z]+}", method=GET)
	@ApiOperation(value = "Get an overviews of one index", notes = "Return an index-overview")
	public IndexResource get(@PathVariable(value="index") String indexId, @PathVariable(value="extension") String extension){
		return assembler.toResource(indexService.gather(indexId));
	}
}