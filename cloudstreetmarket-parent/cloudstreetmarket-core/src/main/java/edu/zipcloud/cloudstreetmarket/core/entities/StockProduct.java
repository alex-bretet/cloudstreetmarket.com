package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="stock")
public class StockProduct extends Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1620238240796817290L;

	private String currency;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "price_id")
	private StockSnapshot price;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "market_id")
	private Market market;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public StockSnapshot getPrice() {
		return price;
	}

	public void setPrice(StockSnapshot price) {
		this.price = price;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}
	
}
