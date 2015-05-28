package edu.zipcloud.cloudstreetmarket.api.links;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.core.entities.Market;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

@Component
public class MarketLinks {

	@Autowired
	private EntityLinks entityLinks;

	/**
	 * Returns the {@link Link} to point to the {@link Market} for the given {@link StockProduct}.
	 * 
	 * @param stock
	 * @return
	 */
	Link getMarketLink(StockProduct stock) {
		return entityLinks.linkToSingleResource(Market.class, stock.getMarket().getId());
	}

	/**
	 * Returns the {@link Link} to the {@link Receipt} of the given {@link Order}.
	 * 
	 * @param order
	 * @return
	 */
	Link getReceiptLink(StockProduct order) {
		//return entityLinks.linkForSingleResource(order).slash(RECEIPT).withRel(RECEIPT_REL);
		return null;
	}
}
