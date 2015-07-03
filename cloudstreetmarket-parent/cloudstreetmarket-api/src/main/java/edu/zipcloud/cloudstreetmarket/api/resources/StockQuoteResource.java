package edu.zipcloud.cloudstreetmarket.api.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;

@XStreamAlias("resource")
public class StockQuoteResource extends Resource<StockQuote> {

	public static final String QUOTES_PATH = "/quotes";
	public static final String STOCK_QUOTES = "stock-quotes";
	public static final String STOCK_QUOTE = "stock-quote";
	public static final String STOCK_QUOTES_PATH = "/stocks";
	
	public StockQuoteResource(StockQuote content, Link... links) {
		super(content, links);
	}
}