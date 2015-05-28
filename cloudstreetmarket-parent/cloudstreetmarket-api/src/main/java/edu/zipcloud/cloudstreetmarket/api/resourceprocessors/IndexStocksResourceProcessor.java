package edu.zipcloud.cloudstreetmarket.api.resourceprocessors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceProcessor;

import edu.zipcloud.cloudstreetmarket.api.links.MarketLinks;
import edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

public class IndexStocksResourceProcessor implements ResourceProcessor<StockProductResource> {

	@Autowired
	private MarketLinks indexLinks;

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.hateoas.ResourceProcessor#process(org.springframework.hateoas.ResourceSupport)
	 */
	@Override
	public StockProductResource process(StockProductResource resource) {
		StockProduct stock = resource.getProduct();
		
		
		
		//resource.add(indexLinks);

		return resource;
	}
}