package edu.zipcloud.cloudstreetmarket.api.assemblers;

import static edu.zipcloud.cloudstreetmarket.api.resources.ChartResource.CHART;
import static edu.zipcloud.cloudstreetmarket.api.resources.ExchangeResource.EXCHANGE;
import static edu.zipcloud.cloudstreetmarket.api.resources.IndustryResource.INDUSTRY;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.ChartIndexController;
import edu.zipcloud.cloudstreetmarket.api.controllers.StockProductController;
import edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

@Component
public class StockProductResourceAssembler extends ResourceAssemblerSupport<StockProduct, StockProductResource> {
	
	@Autowired
	EntityLinks entityLinks;

	public StockProductResourceAssembler() {
	    super(StockProductController.class, StockProductResource.class);
	}
	
	@Override
	public StockProductResource toResource(StockProduct stock) {
		StockProductResource resource = createResourceWithId(stock.getId(), stock);
		if(stock.getIndustry() != null){
			resource.add(entityLinks.linkToSingleResource(stock.getIndustry()).withRel(INDUSTRY));
		}
		
		if(stock.getExchange() != null){
			resource.add(entityLinks.linkToSingleResource(stock.getExchange()).withRel(EXCHANGE));
		}

		resource.add(linkTo(methodOn(ChartIndexController.class).get(stock.getId(), ".png", null, null, null, null, null, null, null)).withRel(CHART));

		return resource;
	}
	
	protected StockProductResource instantiateResource(StockProduct entity) {
		return new StockProductResource(entity);
	}
}