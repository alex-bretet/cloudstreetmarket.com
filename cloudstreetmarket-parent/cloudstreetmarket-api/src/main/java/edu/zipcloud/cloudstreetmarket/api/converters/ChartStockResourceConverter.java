package edu.zipcloud.cloudstreetmarket.api.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.resources.ChartResource;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartStock;
import static edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource.*;

@Component
public class ChartStockResourceConverter implements Converter<ChartStock, ChartResource> {
	
	@Autowired
	private EntityLinks entityLinks;
	
	@Override
	public ChartResource convert(ChartStock stock) {
		ChartResource resource = new ChartResource(stock);
		resource.add(entityLinks.linkToSingleResource(stock));
		resource.add(entityLinks.linkToSingleResource(stock.getStock()).withRel(STOCK));
		return resource;
	}
}