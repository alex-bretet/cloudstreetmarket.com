package edu.zipcloud.cloudstreetmarket.core.entities;

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

@Entity
@Table(name="stock_quote")
@XStreamAlias("stock_quote")
public class StockQuote extends Quote{

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

	//Avoid fetching lazy collections here (session may be closed depending upon where toString is called from)
	@Override
	public String toString() {
		return "StockQuote [stock=" + stock + ", bid=" + bid + ", ask=" + ask
				+ ", symbol=" + symbol + ", id=" + id + "]";
	}
}
