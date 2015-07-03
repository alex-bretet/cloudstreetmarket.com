package edu.zipcloud.cloudstreetmarket.api.controllers;

import static edu.zipcloud.cloudstreetmarket.api.resources.CurrencyExchangeResource.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import edu.zipcloud.cloudstreetmarket.api.assemblers.CurrencyExchangeResourceAssembler;
import edu.zipcloud.cloudstreetmarket.api.resources.CurrencyExchangeResource;
import edu.zipcloud.cloudstreetmarket.api.services.CurrencyExchangeService;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;

@Api(value = CURRENCY_EXCHANGE, description = "Currency Exchanges") // Swagger annotation
@RestController
@ExposesResourceFor(CurrencyExchange.class)
@RequestMapping(value=CURRENCY_EXCHANGES_PATH, produces={"application/xml", "application/json"})
public class CurrencyExchangeController extends CloudstreetApiWCI<CurrencyExchange>{
	
	@Autowired
	private CurrencyExchangeService currencyExchangeService;
	
	@Autowired
	private CurrencyExchangeResourceAssembler assembler;

	@RequestMapping(value="/{currencyX:[a-zA-Z=]+}{extension:\\.[a-z]+}", method=GET)
	@ApiOperation(value = "Get an overviews of one index", notes = "Return an index-overview")
	public CurrencyExchangeResource get(@PathVariable(value="currencyX") String ticker, @PathVariable(value="extension") String extension){
		return assembler.toResource(currencyExchangeService.gather(ticker));
	}
}