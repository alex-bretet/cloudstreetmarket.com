/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
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
		if(index.getExchange() != null){
			resource.add(entityLinks.linkToSingleResource(index.getExchange()).withRel(EXCHANGE));
		}
		resource.add(linkTo(methodOn(ChartIndexController.class).get(index.getId(), ".png", null, null, null, null, null, null, null)).withRel(CHART));
		resource.add(linkTo(methodOn(StockProductController.class).getSeveral(null, null, index.getId(), null, null, null, null)).withRel(COMPONENTS));
	    return resource;
	}
	
	@Override
	protected IndexResource instantiateResource(Index entity) {
		return new IndexResource(entity);
	}
}