package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import edu.zipcloud.cloudstreetmarket.core.dtos.HistoProductDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.IndexOverviewDTO;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketCode;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;
import edu.zipcloud.cloudstreetmarket.core.services.IMarketService;

@Api(value = "indices", description = "Financial indices") // Swagger annotation
@RestController
@RequestMapping(value="/indices", produces={"application/xml", "application/json"})
public class IndexController extends CloudstreetApiWCI {

	@Autowired
	private WebApplicationContext webAppContext;

	@Autowired
	@Qualifier("marketServiceImpl")
	private IMarketService marketService;
	
	@RequestMapping(method=GET)
	@ApiOperation(value = "Get overviews of indices", notes = "Return a page of index-overviews")
	public Page<IndexOverviewDTO> getIndices(
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"dailyLatestValue"}, direction=Direction.DESC) Pageable pageable){
		return marketService.getLastDayIndicesOverview(pageable);
	}
	
	@RequestMapping(value="/{market}", method=GET)
	@ApiOperation(value = "Get overviews of indices filtered by market", notes = "Return a page of index-overviews")
	public Page<IndexOverviewDTO> getIndicesPerMarket(
			@PathVariable MarketCode market,
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"dailyLatestValue"}, direction=Direction.DESC) Pageable pageable){
		return marketService.getLastDayIndicesOverview(market, pageable);
	}

	@RequestMapping(value="/{market}/{index}/histo", method=GET)
	@ApiOperation(value = "Get historical-data for one index", notes = "Return a set of historical-data from one index")
	public HistoProductDTO getHistoIndex(
			@ApiParam(value="Market Code: EUROPE") @PathVariable("market") MarketCode market, 
			@ApiParam(value="Index code: ^OEX") @PathVariable("index") String indexCode,
			@ApiParam(value="Start date: 2014-01-01") @RequestParam(value="fd",defaultValue="") Date fromDate,
			@ApiParam(value="End date: 2020-12-12") 	@RequestParam(value="td",defaultValue="") Date toDate,
			@ApiParam(value="Period between snapshots") @RequestParam(value="i",defaultValue="MINUTE_30") QuotesInterval interval){
		return marketService.getHistoIndex(indexCode, market, fromDate, toDate, interval);
	}
}