package edu.zipcloud.cloudstreetmarket.api.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.MarketController;
import edu.zipcloud.cloudstreetmarket.api.resources.MarketResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;

@Component
public class MarketResourceAssembler extends ResourceAssemblerSupport<Market, MarketResource> {
	
	@Autowired
	EntityLinks entityLinks;
	
	public MarketResourceAssembler() {
	    super(MarketController.class, MarketResource.class);
	}
	
	@Override
	public MarketResource toResource(Market market) {
		return createResourceWithId(market.getId(), market);
	}
	
	protected MarketResource instantiateResource(Market entity) {
		return new MarketResource(entity);
	}
}