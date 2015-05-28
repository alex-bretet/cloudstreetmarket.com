package edu.zipcloud.cloudstreetmarket.api.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import static edu.zipcloud.cloudstreetmarket.api.resources.MarketResource.*;

@Component
public class StockProductResourceConverter implements Converter<StockProduct, StockProductResource> {
	
	@Autowired
	EntityLinks entityLinks;
	
	@Override
	public StockProductResource convert(StockProduct stock) {
		StockProductResource resource = new StockProductResource(stock);
		resource.add(entityLinks.linkToSingleResource(stock));
		resource.add(entityLinks.linkToSingleResource(stock.getMarket()).withRel(MARKET));
		return resource;
	}
}