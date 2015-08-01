package edu.zipcloud.cloudstreetmarket.api.assemblers;

import static edu.zipcloud.cloudstreetmarket.api.resources.TransactionResource.*;
import static edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.TransactionController;
import edu.zipcloud.cloudstreetmarket.api.controllers.StockQuoteController;
import edu.zipcloud.cloudstreetmarket.api.resources.StockQuoteResource;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;

@Component
public class StockQuoteResourceAssembler extends ResourceAssemblerSupport<StockQuote, StockQuoteResource> {
	
	@Autowired
	EntityLinks entityLinks;

	public StockQuoteResourceAssembler() {
	    super(StockQuoteController.class, StockQuoteResource.class);
	}
	
	@Override
	public StockQuoteResource toResource(StockQuote quote) {
		StockQuoteResource resource = createResourceWithId(quote.getId(), quote);
		resource.add(entityLinks.linkToSingleResource(quote.getStock()).withRel(STOCK));
		resource.add(linkTo(methodOn(TransactionController.class).search(null, quote.getId(), null, null, null)).withRel(TRANSACTIONS));
		return resource;
	}
	
	protected StockQuoteResource instantiateResource(StockQuote entity) {
		return new StockQuoteResource(entity);
	}
}