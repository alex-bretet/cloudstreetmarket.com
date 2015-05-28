package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.hateoas.ResourceSupport;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.Product;

@XStreamAlias("product")
public class ProductOverviewDTO extends ResourceSupport implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private String code= null;
	private String name= null;
	private String market= null;
	private String currency= null;
	private BigDecimal latestValue= null;
	private BigDecimal latestChange= null;
	private BigDecimal latestChangePercent= null;
	private BigDecimal prevClose= null;
	private BigDecimal high= null;
	private BigDecimal low= null;
	
	public ProductOverviewDTO(){
	}

	public ProductOverviewDTO(String name, String code, String market, String currency, BigDecimal latestValue, BigDecimal latestChange, BigDecimal latestChangePercent, BigDecimal prevClose, BigDecimal high, BigDecimal low){
		this.name = name;
		this.code = code;
		this.market = market;
		this.latestValue = latestValue;
		this.latestChange = latestChange;
		this.latestChangePercent = latestChangePercent;
		this.prevClose = prevClose;
		this.high = high;
		this.low = low;
		this.currency = currency;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getLatestValue() {
		return latestValue;
	}
	
	public void setLatestValue(BigDecimal latestValue) {
		this.latestValue = latestValue;
	}
	
	public BigDecimal getLatestChange() {
		return latestChange;
	}
	
	public void setLatestChange(BigDecimal latestChange) {
		this.latestChange = latestChange;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public BigDecimal getLatestChangePercent() {
		return latestChangePercent;
	}

	public void setLatestChangePercent(BigDecimal latestChangePercent) {
		this.latestChangePercent = latestChangePercent;
	}

	public BigDecimal getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(BigDecimal prevClose) {
		this.prevClose = prevClose;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public static ProductOverviewDTO build(Product product){
		return new ProductOverviewDTO(
				product.getName(), 
				product.getId(),
				(product.getMarket()!=null)? product.getMarket().getName(): null, 
				product.getCurrency(), 
				product.getDailyLatestValue(), 
				product.getDailyLatestChange(), 
				product.getDailyLatestChangePercent(), 
				product.getPreviousClose(), 
				product.getHigh(), 
				product.getLow());
	}
}