package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

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

	private static final long serialVersionUID = 1620238240796817290L;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockProduct other = (StockProduct) obj;
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
		return "StockProduct [id=" + getId() + ", name=" + getName()
				+ ", dailyLatestValue()=" + getDailyLatestValue()
				+ ", dailyLatestChange()=" + getDailyLatestChange()
				+ ", dailyLatestChangePercent()="
				+ getDailyLatestChangePercent() + ", previousClose()="
				+ getPreviousClose() + ", high()=" + getHigh()
				+ ", low()=" + getLow() + ", industry=" + industry.getLabel() + ", currency()=" + getCurrency() + "]";
	}
}