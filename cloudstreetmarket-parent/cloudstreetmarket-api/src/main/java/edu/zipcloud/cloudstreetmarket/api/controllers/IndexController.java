package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import edu.zipcloud.cloudstreetmarket.api.converters.IndexResourceConverter;
import edu.zipcloud.cloudstreetmarket.api.resources.IndexResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.services.IndexService;
import static edu.zipcloud.cloudstreetmarket.api.resources.IndexResource.*;

@Api(value = INDICES, description = "Financial indices") // Swagger annotation
@RestController
@ExposesResourceFor(Index.class)
@RequestMapping(value=INDICES_PATH, produces={"application/xml", "application/json"})
public class IndexController extends CloudstreetApiWCI {
	
	@Autowired
	private IndexService indexService;

	@Autowired
	private IndexResourceConverter converter;
	
	@RequestMapping(method=GET)
	@ApiOperation(value = "Get overviews of indices", notes = "Return a page of index-overviews")
	public Page<IndexResource> getSeveral(
			@RequestParam(value="market", required=false) MarketId marketId,
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"dailyLatestValue"}, direction=Direction.DESC) Pageable pageable){
		return indexService.getIndicesByMarket(marketId, pageable).map(converter);
	}
	
	@RequestMapping(value="/{index}", method=GET)
	@ApiOperation(value = "Get an overviews of one index", notes = "Return an index-overview")
	public IndexResource get(@PathVariable(value="index") String indexId){
		return converter.convert(indexService.getIndex(indexId));
	}
}