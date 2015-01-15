package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="index_product")
public class IndexProduct extends Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2919348303931939346L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "market_id", nullable=true)
	private Market market;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "stock_indices", joinColumns = { @JoinColumn(name = "index_code") },
			inverseJoinColumns = { @JoinColumn(name = "stock_code") })
	private Set<StockProduct> stocks = new LinkedHashSet<StockProduct>();

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public Set<StockProduct> getStocks() {
		return stocks;
	}

	public void setStocks(Set<StockProduct> stocks) {
		this.stocks = stocks;
	}
	
}
