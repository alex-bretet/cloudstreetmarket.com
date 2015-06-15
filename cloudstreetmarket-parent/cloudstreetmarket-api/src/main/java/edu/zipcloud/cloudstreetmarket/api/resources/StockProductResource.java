package edu.zipcloud.cloudstreetmarket.api.resources;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

@XStreamAlias("resource")
public class StockProductResource extends ResourceSupport implements Serializable  {

	public static final String COMPONENTS = "components";
	public static final String STOCK = "stock";
	public static final String STOCKS = "stocks";
	public static final String STOCKS_PATH = "/stocks";
	
	private static final long serialVersionUID = 3681041931966104346L;
	
	public StockProductResource() {
	}
	
	@XStreamAlias("stock")
	private StockProduct product;

	public StockProductResource(StockProduct product) {
		super();
		this.product = product;
	}

	public StockProduct getProduct() {
		return product;
	}
	
	public void setProduct(StockProduct product) {
		this.product = product;
	}
	
	@Override
	public String toString() {
		return "StockProductResource [product=" + product + "]";
	}
}