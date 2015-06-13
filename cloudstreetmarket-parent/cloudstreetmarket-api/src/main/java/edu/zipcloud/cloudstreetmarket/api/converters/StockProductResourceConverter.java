package edu.zipcloud.cloudstreetmarket.api.converters;

import static edu.zipcloud.cloudstreetmarket.api.resources.ExchangeResource.EXCHANGE;
import static edu.zipcloud.cloudstreetmarket.api.resources.IndexResource.INDICES;
import static edu.zipcloud.cloudstreetmarket.api.resources.IndustryResource.INDUSTRY;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.IndexController;
import edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

@Component
public class StockProductResourceConverter implements Converter<StockProduct, StockProductResource> {
	
	@Autowired
	EntityLinks entityLinks;
	
	@Override
	public StockProductResource convert(StockProduct stock) {
		StockProductResource resource = new StockProductResource(stock);
		
		resource.add(entityLinks.linkToSingleResource(stock));

		if(stock.getIndustry() != null){
			resource.add(entityLinks.linkToSingleResource(stock.getIndustry()).withRel(INDUSTRY));
		}
		
		if(stock.getExchange() != null){
			resource.add(entityLinks.linkToSingleResource(stock.getExchange()).withRel(EXCHANGE));
			resource.add(linkTo(methodOn(IndexController.class).getSeveral(stock.getExchange().getId(), null, null)).withRel(INDICES));
		}

		return resource;
	}
}