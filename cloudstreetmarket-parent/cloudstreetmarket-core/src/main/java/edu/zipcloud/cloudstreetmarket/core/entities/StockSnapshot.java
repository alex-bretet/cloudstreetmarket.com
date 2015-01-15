package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="stock_snapshot")
public class StockSnapshot extends ProductSnapshot implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8175317254623555447L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stock_code")
	private StockProduct stock;
	
	private double bid;
	
	private double ask;

	public StockProduct getStock() {
		return stock;
	}

	public void setStock(StockProduct stock) {
		this.stock = stock;
	}

	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public double getAsk() {
		return ask;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}
	
}
