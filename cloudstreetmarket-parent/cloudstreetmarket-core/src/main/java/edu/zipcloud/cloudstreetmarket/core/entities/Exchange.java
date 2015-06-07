package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableSerializer;
import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableToIdConverter;

@Entity
public class Exchange extends AbstractId<String> {
	
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "market_id", nullable=true)
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("marketId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("marketId")
	private Market market;
	
	@OneToMany(mappedBy = "exchange", cascade = { CascadeType.ALL }, fetch=FetchType.LAZY)
	@JsonIgnore
	@XStreamOmitField
	private Set<Index> indices = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "exchange", cascade = { CascadeType.ALL }, fetch=FetchType.LAZY)
	@JsonIgnore
	@XStreamOmitField
	private Set<StockProduct> stocks = new LinkedHashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public Set<Index> getIndices() {
		return indices;
	}

	public void setIndices(Set<Index> indices) {
		this.indices = indices;
	}

	public Set<StockProduct> getStocks() {
		return stocks;
	}

	public void setStocks(Set<StockProduct> stocks) {
		this.stocks = stocks;
	}
}