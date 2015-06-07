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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((market == null) ? 0 : market.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarketResource other = (MarketResource) obj;
		if (market == null) {
			if (other.market != null)
				return false;
		} else if (!market.equals(other.market))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "MarketResource [market=" + market.toString() + "]";
	}
}