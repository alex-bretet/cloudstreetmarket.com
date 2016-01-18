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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.social.yahoo.module.YahooQuote.Builder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableSerializer;
import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableToIdConverter;

@Entity
@Table(name="stock_product")
@NamedEntityGraph(name="StockProduct.indices", attributeNodes={
    @NamedAttributeNode("indices")
})
public class StockProduct extends Product implements Serializable{

	private static final long serialVersionUID = -6533148398875178571L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("industryId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("industryId")
    private Industry industry;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("exchangeId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("exchangeId")
    private Exchange exchange;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JsonIgnore
	@XStreamOmitField
	private Set<Index> indices = new LinkedHashSet<>();

	@OneToOne(fetch = FetchType.EAGER, optional=true, cascade=CascadeType.ALL)
	@JoinColumn(name = "quote_id")
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("quoteId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("quoteId")
	private StockQuote quote;
	
	public StockProduct() {
	}
	
	public StockProduct(String id) {
		setId(id);
	}

	public StockProduct(String id, String name, BigDecimal high,
			BigDecimal low, BigDecimal dailyLatestValue,
			BigDecimal dailyLatestChange, BigDecimal dailyLatestChangePercent,
			BigDecimal previousClose, BigDecimal open, Exchange exchange,
			String currency) {
		this.id = id;
		this.name = name;
		this.high = high;
		this.low = low;
		this.dailyLatestValue = dailyLatestValue;
		this.dailyLatestChange = dailyLatestChange;
		this.dailyLatestChangePercent = dailyLatestChangePercent;
		this.previousClose = previousClose;
		this.open = open;
		this.exchange = exchange;
		this.currency = currency;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public Exchange getExchange() {
		return exchange;
	}

	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}

	public Set<Index> getIndices() {
		return indices;
	}

	public void setIndices(Set<Index> indices) {
		this.indices = indices;
	}
	
	public StockQuote getQuote() {
		return quote;
	}

	public void setQuote(StockQuote quote) {
		this.quote = quote;
	}

	public static class Builder {

    	private String id;
    	private String name;
    	private BigDecimal high;
    	private BigDecimal low;
    	private BigDecimal dailyLatestValue;
    	private BigDecimal dailyLatestChange;
    	private BigDecimal dailyLatestChangePercent;
    	private BigDecimal previousClose;
    	private BigDecimal open;
    	private Exchange exchange;
    	private String currency;
    	
        public Builder withId(String id) {
        	this.id = id;
            return this;
        }
        
        public Builder withName(String name) {
        	this.name = name;
            return this;
        }
        
        public Builder withOpen(BigDecimal open) {
        	this.open = open;
            return this;
        }
        
        public Builder withPreviousClose(BigDecimal previousClose) {
        	this.previousClose = previousClose;
            return this;
        }
        
        public Builder withDailyLatestValue(BigDecimal dailyLatestValue) {
        	this.dailyLatestValue = dailyLatestValue;
            return this;
        }
        
        public Builder withDailyLatestChange(BigDecimal dailyLatestChange) {
        	this.dailyLatestChange = dailyLatestChange;
            return this;
        }
        
        public Builder withDailyLatestChangePercent(BigDecimal dailyLatestChangePercent) {
        	this.dailyLatestChangePercent = dailyLatestChangePercent;
            return this;
        }
        
        public Builder withHigh(BigDecimal high) {
        	this.high = high;
            return this;
        }
        
        public Builder withLow(BigDecimal low) {
        	this.low = low;
            return this;
        }

        public Builder withExchange(Exchange exchange) {
        	this.exchange = exchange;
            return this;
        }
        
        public Builder withCurrency(String currency) {
        	this.currency = currency;
            return this;
        }

        public StockProduct build() {
            return new StockProduct(id, name, high, low, dailyLatestValue, dailyLatestChange, dailyLatestChangePercent, previousClose, open, exchange, currency);
        }
    }
	
	@Override
	public String toString() {
		return "StockProduct [industry=" + industry + ", exchange=" + exchange
				+ ", name=" + name + ", dailyLatestValue=" + dailyLatestValue
				+ ", dailyLatestChange=" + dailyLatestChange
				+ ", dailyLatestChangePercent=" + dailyLatestChangePercent
				+ ", previousClose=" + previousClose + ", high=" + high
				+ ", low=" + low + ", currency=" + currency + ", id=" + id
				+ "]";
	}
}