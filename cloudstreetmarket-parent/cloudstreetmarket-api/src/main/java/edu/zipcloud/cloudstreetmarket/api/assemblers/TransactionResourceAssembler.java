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

import static edu.zipcloud.cloudstreetmarket.api.resources.StockQuoteResource.STOCK_QUOTE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.TransactionController;
import edu.zipcloud.cloudstreetmarket.api.resources.TransactionResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;

@Component
public class TransactionResourceAssembler extends ResourceAssemblerSupport<Transaction, TransactionResource> {
	
	@Autowired
	EntityLinks entityLinks;

	public TransactionResourceAssembler() {
	    super(TransactionController.class, TransactionResource.class);
	}
	
	@Override
	public TransactionResource toResource(Transaction transaction) {
		TransactionResource resource = createResourceWithId(transaction.getId(), transaction);
		resource.add(entityLinks.linkToSingleResource(transaction.getQuote()).withRel(STOCK_QUOTE));
		return resource;
	}
	
	protected TransactionResource instantiateResource(Transaction entity) {
		return new TransactionResource(entity);
	}
}