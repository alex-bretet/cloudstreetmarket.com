package edu.zipcloud.cloudstreetmarket.api.converters;

import static edu.zipcloud.cloudstreetmarket.api.resources.IndexResource.*;
import static edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.IndexController;
import edu.zipcloud.cloudstreetmarket.api.controllers.StockProductController;
import edu.zipcloud.cloudstreetmarket.api.resources.ExchangeResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;

@Component
public class ExchangeResourceConverter implements Converter<Exchange, ExchangeResource> {
	
	@Autowired
	private EntityLinks entityLinks;
	
	@Override
	public ExchangeResource convert(Exchange exchange) {
		ExchangeResource resource = new ExchangeResource(exchange);

		resource.add(entityLinks.linkToSingleResource(exchange.getMarket()));
		resource.add(linkTo(methodOn(IndexController.class).getSeveral(exchange.getId(), null, null)).withRel(INDICES));
		resource.add(linkTo(methodOn(StockProductController.class).getSeveral(null, exchange.getId(), null, null, null, null, null)).withRel(STOCKS));

		return resource;
	}
}