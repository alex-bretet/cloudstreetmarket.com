package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import edu.zipcloud.cloudstreetmarket.api.converters.HistoricalIndexResourceConverter;
import edu.zipcloud.cloudstreetmarket.api.resources.HistoricalIndexResource;
import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;
import static edu.zipcloud.cloudstreetmarket.api.resources.HistoricalIndexResource.*;
import static edu.zipcloud.cloudstreetmarket.api.resources.IndexResource.*;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;
import edu.zipcloud.cloudstreetmarket.core.services.IndexService;

@Api(value = "indices historical", description = "Financial indices, historical data") // Swagger annotation
@RestController
@ExposesResourceFor(HistoricalIndex.class)
@RequestMapping(value=INDICES_PATH, produces={"application/xml", "application/json"})
public class HistoIndexController extends CloudstreetApiWCI {
	
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private HistoricalIndexResourceConverter converter;

	@RequestMapping(value="/{index}"+HISTO_PATH, method=GET)
	@ApiOperation(value = "Get historical-data for one index", notes = "Return a set of historical-data from one index")
	public Set<HistoricalIndexResource> getSeveral(
			@ApiParam(value="Index ID: ^OEX") @PathVariable("index") String indexId,
			@ApiParam(value="Start date: 2014-01-01") @RequestParam(value="fd", defaultValue="") Date fromDate,
			@ApiParam(value="End date: 2020-12-12") @RequestParam(value="td", defaultValue="") Date toDate,
			@ApiParam(value="Period between snapshots") @RequestParam(value="i", defaultValue="MINUTE_30") QuotesInterval interval){
		return indexService.getHistoricalIndexData(indexId, fromDate, toDate, interval)
				.stream()
				.map(h -> HistoricalIndexResource.build(h))
				.collect(Collectors.toSet());
	}
}