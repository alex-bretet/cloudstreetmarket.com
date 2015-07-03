package edu.zipcloud.cloudstreetmarket.api.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;

@XStreamAlias("resource")
public class CurrencyExchangeResource extends Resource<CurrencyExchange> {
	
	public static final String CURRENCY_EXCHANGE = "currencyX";
	public static final String CURRENCY_EXCHANGES = "currencyX";
	public static final String CURRENCY_EXCHANGES_PATH = "/currencyX";

	public CurrencyExchangeResource(CurrencyExchange content, Link... links) {
		super(content, links);
	}
}