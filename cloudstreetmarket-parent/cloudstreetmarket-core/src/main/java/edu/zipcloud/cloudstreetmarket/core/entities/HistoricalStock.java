package edu.zipcloud.cloudstreetmarket.core.entities;

import static edu.zipcloud.cloudstreetmarket.core.entities.HistoricalStock.DISCR;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableSerializer;
import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableToIdConverter;

@Entity
@DiscriminatorValue(DISCR)
@XStreamAlias("histo_stock")
public class HistoricalStock extends Historic implements Serializable {

	private static final long serialVersionUID = -802306391915956578L;
	public static final String DISCR = "stk";
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stock_code")
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("stockId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("stockId")
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
