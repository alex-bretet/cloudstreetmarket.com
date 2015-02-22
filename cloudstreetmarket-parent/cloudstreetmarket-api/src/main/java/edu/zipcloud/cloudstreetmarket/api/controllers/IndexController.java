package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.Api;

import edu.zipcloud.cloudstreetmarket.core.dtos.HistoProductDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.IndexOverviewDTO;
import edu.zipcloud.cloudstreetmarket.core.params.MarketCodeParam;
import edu.zipcloud.cloudstreetmarket.core.params.QuotesIntervalParam;
import edu.zipcloud.cloudstreetmarket.core.params.SortDirectionParam;
import edu.zipcloud.cloudstreetmarket.core.params.SortFieldParam;
import edu.zipcloud.cloudstreetmarket.core.services.IMarketService;
import edu.zipcloud.util.SortUtil;

@Api(value = "indices", description = "Financial indices") // Swagger annotation
@RestController
@RequestMapping(value="/indices", produces={"application/xml", "application/json"})
public class IndexController extends CloudstreetApiWCI{

	@Autowired
	private WebApplicationContext webAppContext;

	@Autowired
	@Qualifier("marketServiceImpl")
	private IMarketService marketService;
	
	@RequestMapping(method=GET)
	public Page<IndexOverviewDTO> getIndices(
			@RequestParam(value="sf", defaultValue="dailyLatestValue") SortFieldParam sortFields, 
			@RequestParam(value="sd", defaultValue="desc") SortDirectionParam sortDirections, 
			@RequestParam(value="pn", defaultValue="0") int pageNumber, 
			@RequestParam(value="ps", defaultValue="10") int pageSize){
		return marketService.getLastDayIndicesOverview(
			new PageRequest(
					pageNumber, 
					pageSize, 
					SortUtil.buildSort(sortFields.getFields(), sortDirections.getDirections()))
		);
	}
	
	@RequestMapping(value="/{market}", method=GET)
	public Page<IndexOverviewDTO> getIndicesPerMarket(@PathVariable MarketCodeParam market, 
			@RequestParam(value="sf", defaultValue="dailyLatestValue") SortFieldParam sortFields, 
			@RequestParam(value="sd", defaultValue="desc") SortDirectionParam sortDirections, 
			@RequestParam(value="pn", defaultValue="0") int pageNumber, 
			@RequestParam(value="ps", defaultValue="10") int pageSize){
		return marketService.getLastDayIndicesOverview(
			market.getValue(), 
			new PageRequest(
				pageNumber, 
				pageSize, 
				SortUtil.buildSort(sortFields.getFields(), sortDirections.getDirections()))
		);
	}
	
	@RequestMapping(value="/{market}/{index}/histo", method=GET)
	public HistoProductDTO getHistoIndex(
			@PathVariable("market") MarketCodeParam market, 
			@PathVariable("index") String indexCode,
			@RequestParam(value="fd",defaultValue="") Date fromDate,
			@RequestParam(value="td",defaultValue="") Date toDate,
			@RequestParam(value="i",defaultValue="MINUTE_30") QuotesIntervalParam interval){
		return marketService.getHistoIndex(indexCode, market.getValue(), fromDate, toDate, interval.getValue());
	}


}