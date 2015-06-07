package edu.zipcloud.cloudstreetmarket.api.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.resources.MarketResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;

@Component
public class MarketResourceConverter implements Converter<Market, MarketResource> {
	
	@Autowired
	EntityLinks entityLinks;
	
	@Override
	public MarketResource convert(Market market) {
		MarketResource resource = new MarketResource(market);
		resource.add(entityLinks.linkToSingleResource(market));
		return resource;
	}
}