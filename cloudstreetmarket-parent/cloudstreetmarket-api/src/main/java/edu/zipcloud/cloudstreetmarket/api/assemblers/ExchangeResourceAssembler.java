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

import static edu.zipcloud.cloudstreetmarket.api.resources.IndexResource.*;
import static edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.ExchangeController;
import edu.zipcloud.cloudstreetmarket.api.controllers.IndexController;
import edu.zipcloud.cloudstreetmarket.api.controllers.StockProductController;
import edu.zipcloud.cloudstreetmarket.api.resources.ExchangeResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;

@Component
public class ExchangeResourceAssembler extends ResourceAssemblerSupport<Exchange, ExchangeResource> {

	@Autowired
	private EntityLinks entityLinks;
	
	public ExchangeResourceAssembler() {
	    super(ExchangeController.class, ExchangeResource.class);
	}
	
	@Override
	public ExchangeResource toResource(Exchange exchange) {
		ExchangeResource resource = createResourceWithId(exchange.getId(), exchange);
		resource.add(entityLinks.linkToSingleResource(exchange.getMarket()));
		resource.add(linkTo(methodOn(IndexController.class).getSeveral(exchange.getId(), null, null)).withRel(INDICES));
		resource.add(linkTo(methodOn(StockProductController.class).getSeveral(null, exchange.getId(), null, null, null, null, null)).withRel(STOCKS));
		return resource;
	}
	
	protected ExchangeResource instantiateResource(Exchange entity) {
		return new ExchangeResource(entity);
	}
}