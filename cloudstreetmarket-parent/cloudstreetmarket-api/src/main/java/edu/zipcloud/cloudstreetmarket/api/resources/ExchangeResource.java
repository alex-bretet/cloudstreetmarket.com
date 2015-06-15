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

	public ExchangeResource() {
	}
	
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
	public String toString() {
		return "ExchangeResource [exchange=" + exchange + "]";
	}
}