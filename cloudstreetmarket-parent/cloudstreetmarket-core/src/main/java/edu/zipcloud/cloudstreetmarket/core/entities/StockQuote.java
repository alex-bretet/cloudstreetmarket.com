/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import edu.zipcloud.cloudstreetmarket.core.entities.Transaction.Builder;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

@Entity
@Table(name="stock_quote")
@XStreamAlias("stock_quote")
public class StockQuote extends Quote{

	private static final long serialVersionUID = -6816461369257289514L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stock_code")
	@XStreamOmitField
	@JsonIgnore
	private StockProduct stock;
	
	@Column(precision = 10, scale = 5)
	private double bid;
	
	@Column(precision = 10, scale = 5)
	private double ask;

	@Column(name="last_change", precision = 10, scale = 5)
	private double lastChange;
	
	@Column(name="last_change_pc", precision = 10, scale = 5)
	private double lastChangePercent;
	
	private String exchange;
	
	private String currency;
	
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
		if(symbol != null){
			return symbol;
		}
		else{
			symbol = stock.getId();
			return symbol;
		}
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getLastChange() {
		return lastChange;
	}

	public void setLastChange(double lastChange) {
		this.lastChange = lastChange;
	}

	public double getLastChangePercent() {
		return lastChangePercent;
	}

	public void setLastChangePercent(double lastChangePercent) {
		this.lastChangePercent = lastChangePercent;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	
	public String getCurrency() {
		return currency;
	}

	public SupportedCurrency getSupportedCurrency() {
		return SupportedCurrency.valueOf(currency.toUpperCase());
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
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
		setLastChange(q.getLastChange());
		setLastChangePercent(q.getLastChangePercent());
		setExchange(q.getExchange());
		setCurrency(q.getCurrency());
		setPreviousClose(q.getPreviousClose());
	}

	public StockQuote(org.springframework.social.yahoo.module.YahooQuote q, StockProduct stockProduct) {
		this(q);
		setStock(stockProduct);
	}
	
	public StockQuote(int id) {
		setId(Long.valueOf(id));
	}

	@Override
	public String toString() {
		return "StockQuote [bid=" + bid + ", ask=" + ask
				+ ", symbol=" + symbol + ", id=" + id + "]";
	}
}
