package edu.zipcloud.cloudstreetmarket.api.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;

@XStreamAlias("resource")
public class ExchangeResource extends Resource<Exchange> {

	public static final String EXCHANGE = "exchange";
	public static final String EXCHANGES = "exchanges";
	public static final String EXCHANGES_PATH = "/exchanges";
	
	public ExchangeResource(Exchange content, Link... links) {
		super(content, links);
	}
}