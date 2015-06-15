package edu.zipcloud.cloudstreetmarket.api.assemblers;

import static edu.zipcloud.cloudstreetmarket.api.resources.IndexResource.INDICES;
import static edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource.STOCKS;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.ExchangeController;
import edu.zipcloud.cloudstreetmarket.api.controllers.IndexController;
import edu.zipcloud.cloudstreetmarket.api.controllers.StockProductController;
import edu.zipcloud.cloudstreetmarket.api.resources.ExchangeResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;

@Component
public class ExchangeResourceAssembler extends ResourceAssemblerSupport<Exchange, ExchangeResource> {

	@Autowired
	private EntityLinks entityLinks;
	
	public ExchangeResourceAssembler() {
	    super(ExchangeController.class, ExchangeResource.class);
	}
	
	@Override
	public ExchangeResource toResource(Exchange exchange) {
		ExchangeResource resource = createResourceWithId(exchange.getId(), exchange);
		resource.setExchange(exchange);
		resource.add(entityLinks.linkToSingleResource(exchange.getMarket()));
		resource.add(linkTo(methodOn(IndexController.class).getSeveral(exchange.getId(), null, null)).withRel(INDICES));
		resource.add(linkTo(methodOn(StockProductController.class).getSeveral(null, exchange.getId(), null, null, null, null, null)).withRel(STOCKS));
		return resource;
	}
}