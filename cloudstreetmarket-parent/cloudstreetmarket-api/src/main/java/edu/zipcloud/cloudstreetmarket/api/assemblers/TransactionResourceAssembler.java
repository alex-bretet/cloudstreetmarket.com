package edu.zipcloud.cloudstreetmarket.api.assemblers;

import static edu.zipcloud.cloudstreetmarket.api.resources.StockQuoteResource.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.TransactionController;
import edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource;
import edu.zipcloud.cloudstreetmarket.api.resources.TransactionResource;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
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
	
	protected StockProductResource instantiateResource(StockProduct entity) {
		return new StockProductResource(entity);
	}
}