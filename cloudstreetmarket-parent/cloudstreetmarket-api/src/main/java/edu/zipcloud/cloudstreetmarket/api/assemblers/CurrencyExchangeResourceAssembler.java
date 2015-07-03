package edu.zipcloud.cloudstreetmarket.api.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.CurrencyExchangeController;
import edu.zipcloud.cloudstreetmarket.api.resources.CurrencyExchangeResource;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;

@Component
public class CurrencyExchangeResourceAssembler extends ResourceAssemblerSupport<CurrencyExchange, CurrencyExchangeResource> {
	
	@Autowired
	private EntityLinks entityLinks;

	public CurrencyExchangeResourceAssembler() {
	    super(CurrencyExchangeController.class, CurrencyExchangeResource.class);
	}
	
	@Override
	public CurrencyExchangeResource toResource(CurrencyExchange currencyExchange) {
	    return createResourceWithId(currencyExchange.getId(), currencyExchange);
	}
	
	@Override
	protected CurrencyExchangeResource instantiateResource(CurrencyExchange entity) {
		return new CurrencyExchangeResource(entity);
	}
}