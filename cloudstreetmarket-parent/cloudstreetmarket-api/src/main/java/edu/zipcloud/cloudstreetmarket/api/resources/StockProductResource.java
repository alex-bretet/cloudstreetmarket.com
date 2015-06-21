package edu.zipcloud.cloudstreetmarket.api.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

@XStreamAlias("resource")
public class StockProductResource extends Resource<StockProduct> {

	public static final String COMPONENTS = "components";
	public static final String STOCK = "stock";
	public static final String STOCKS = "stocks";
	public static final String STOCKS_PATH = "/stocks";
	
	public StockProductResource(StockProduct content, Link... links) {
		super(content, links);
	}
}