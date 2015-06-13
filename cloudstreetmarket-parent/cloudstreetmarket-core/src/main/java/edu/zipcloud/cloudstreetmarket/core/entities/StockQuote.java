package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Table(name="stock_quote")
@XStreamAlias("stock_quote")
public class StockQuote extends Quote implements Serializable{

	private static final long serialVersionUID = -8175317254623555447L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stock_code")
	@XStreamOmitField
	@JsonIgnore
	private StockProduct stock;
	
	private double bid;
	
	private double ask;

	@Transient
	private String symbol;
	
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

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public StockQuote(){
	}

	public StockQuote(org.springframework.social.yahoo.module.YahooQuote q){
		setAsk(q.getAsk());
		setBid(q.getBid());
		setDate(new Date());
		setHigh(q.getHigh());
		setLow(q.getLow());
		setLast(q.getLast());
		setOpen(q.getOpen());
		setSymbol(q.getId());
		setPreviousClose(q.getPreviousClose());
	}

	public StockQuote(org.springframework.social.yahoo.module.YahooQuote q, StockProduct stockProduct) {
		this(q);
		setStock(stockProduct);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockQuote other = (StockQuote) obj;
		if (getId() != other.getId())
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "StockQuote [stock=" + stock + ", bid=" + bid + ", ask=" + ask
				+ ", getOpen()=" + getOpen() + ", getPreviousClose()="
				+ getPreviousClose() + ", getLast()=" + getLast()
				+ ", getHigh()=" + getHigh() + ", getLow()=" + getLow()
				+ ", getId()=" + getId() + "]";
	}
	
	
}
