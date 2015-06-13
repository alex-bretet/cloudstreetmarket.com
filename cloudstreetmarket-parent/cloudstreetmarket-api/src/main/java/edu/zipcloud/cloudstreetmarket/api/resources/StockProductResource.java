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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockProductResource other = (StockProductResource) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "StockProductResource [product=" + product.toString() + "]";
	}
}