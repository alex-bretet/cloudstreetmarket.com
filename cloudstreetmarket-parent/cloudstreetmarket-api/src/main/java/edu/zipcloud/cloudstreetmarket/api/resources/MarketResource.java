package edu.zipcloud.cloudstreetmarket.api.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;

@XStreamAlias("resource")
public class MarketResource extends Resource<Market> {

	public static final String MARKET = "market";
	public static final String MARKETS = "markets";
	public static final String MARKETS_PATH = "/markets";
	
	public MarketResource(Market content, Link... links) {
		super(content, links);
	}
}