package edu.zipcloud.cloudstreetmarket.api.converters;

import static edu.zipcloud.cloudstreetmarket.api.resources.MarketResource.*;
import static edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.MarketController;
import edu.zipcloud.cloudstreetmarket.api.controllers.StockProductController;
import edu.zipcloud.cloudstreetmarket.api.resources.IndexResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;

@Component
public class IndexResourceConverter implements Converter<Index, IndexResource> {
	
	@Autowired
	EntityLinks entityLinks;
	
	@Override
	public IndexResource convert(Index index) {
		IndexResource resource = new IndexResource(index);
		resource.add(entityLinks.linkToSingleResource(index));
		resource.add(linkTo(methodOn(MarketController.class).get(index.getMarket().getId(), null)).withRel(MARKET));
		resource.add(linkTo(methodOn(StockProductController.class).getSeveral(null, index.getId(), null, null, null)).withRel(STOCKS));
		return resource;
	}
}