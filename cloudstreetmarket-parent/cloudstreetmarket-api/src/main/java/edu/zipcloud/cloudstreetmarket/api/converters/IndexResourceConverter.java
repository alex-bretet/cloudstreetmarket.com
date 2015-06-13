package edu.zipcloud.cloudstreetmarket.api.converters;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static edu.zipcloud.cloudstreetmarket.api.resources.ChartResource.*;
import static edu.zipcloud.cloudstreetmarket.api.resources.ExchangeResource.EXCHANGE;
import static edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource.COMPONENTS;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.ChartIndexController;
import edu.zipcloud.cloudstreetmarket.api.controllers.StockProductController;
import edu.zipcloud.cloudstreetmarket.api.resources.IndexResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;

@Component
public class IndexResourceConverter implements Converter<Index, IndexResource> {
	
	@Autowired
	private EntityLinks entityLinks;
	
	@Override
	public IndexResource convert(Index index) {
		IndexResource resource = new IndexResource(index);
		resource.add(entityLinks.linkToSingleResource(index));
		resource.add(entityLinks.linkToSingleResource(index.getExchange()).withRel(EXCHANGE));
		try {
			resource.add(linkTo(methodOn(ChartIndexController.class).get(index.getId(), null, null, null, null, null, null, null, null)).withRel(CHART));
		} catch (IOException e) {
		}
		resource.add(linkTo(methodOn(StockProductController.class).getSeveral(null, null, index.getId(), null, null, null, null)).withRel(COMPONENTS));
		return resource;
	}
}