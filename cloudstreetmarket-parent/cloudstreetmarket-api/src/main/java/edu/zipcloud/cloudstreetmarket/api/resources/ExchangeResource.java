package edu.zipcloud.cloudstreetmarket.api.resources;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;

@XStreamAlias("resource")
public class ExchangeResource extends ResourceSupport implements Serializable{

	public static final String EXCHANGE = "exchange";
	public static final String EXCHANGES = "exchanges";
	public static final String EXCHANGES_PATH = "/exchanges";
	
	private static final long serialVersionUID = 1L;
	private Exchange exchange;

	public ExchangeResource(Exchange exchange) {
		super();
		this.exchange = exchange;
	}
	
	public Exchange getExchange() {
		return exchange;
	}
	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((exchange == null) ? 0 : exchange.hashCode());
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
		ExchangeResource other = (ExchangeResource) obj;
		if (exchange == null) {
			if (other.exchange != null)
				return false;
		} else if (!exchange.equals(other.exchange))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ExchangeResource [exchange=" + exchange.toString() + "]";
	}
}