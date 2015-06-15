package edu.zipcloud.cloudstreetmarket.api.resources;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.Market;

@XStreamAlias("resource")
public class MarketResource extends ResourceSupport implements Serializable{

	public static final String MARKET = "market";
	public static final String MARKETS = "markets";
	public static final String MARKETS_PATH = "/markets";
	
	private static final long serialVersionUID = 1L;
	private Market market;

	public MarketResource() {
	}
	
	public MarketResource(Market market) {
		super();
		this.market = market;
	}
	
	public Market getMarket() {
		return market;
	}
	public void setMarket(Market market) {
		this.market = market;
	}
	
	@Override
	public String toString() {
		return "MarketResource [market=" + market + "]";
	}
}