package edu.zipcloud.cloudstreetmarket.api.assemblers;

import static edu.zipcloud.cloudstreetmarket.api.resources.ChartResource.CHART;
import static edu.zipcloud.cloudstreetmarket.api.resources.ExchangeResource.EXCHANGE;
import static edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource.COMPONENTS;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.ChartIndexController;
import edu.zipcloud.cloudstreetmarket.api.controllers.IndexController;
import edu.zipcloud.cloudstreetmarket.api.controllers.StockProductController;
import edu.zipcloud.cloudstreetmarket.api.resources.IndexResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;

@Component
public class IndexResourceAssembler extends ResourceAssemblerSupport<Index, IndexResource> {
	
	@Autowired
	private EntityLinks entityLinks;

	public IndexResourceAssembler() {
	    super(IndexController.class, IndexResource.class);
	}
	
	@Override
	public IndexResource toResource(Index index) {
		IndexResource resource = createResourceWithId(index.getId(), index);
		resource.add(entityLinks.linkToSingleResource(index.getExchange()).withRel(EXCHANGE));
		resource.add(linkTo(methodOn(ChartIndexController.class).get(index.getId(), ".png", null, null, null, null, null, null, null)).withRel(CHART));
		resource.add(linkTo(methodOn(StockProductController.class).getSeveral(null, null, index.getId(), null, null, null, null)).withRel(COMPONENTS));
	    return resource;
	}
	
	@Override
	protected IndexResource instantiateResource(Index entity) {
		return new IndexResource(entity);
	}
}