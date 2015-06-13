package edu.zipcloud.cloudstreetmarket.api.converters;

import static edu.zipcloud.cloudstreetmarket.api.resources.ChartResource.CHART;
import static edu.zipcloud.cloudstreetmarket.api.resources.ExchangeResource.EXCHANGE;
import static edu.zipcloud.cloudstreetmarket.api.resources.IndustryResource.INDUSTRY;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.api.controllers.ChartIndexController;
import edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

@Component
@Transactional(readOnly=true)
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
		}

		try {
			resource.add(linkTo(methodOn(ChartIndexController.class).get(stock.getId(), null, null, null, null, null, null, null, null)).withRel(CHART));
		} catch (IOException e) {
		}

		return resource;
	}
}