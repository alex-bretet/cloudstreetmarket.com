package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="stock_product")
@NamedEntityGraph(name = "Index.detail", attributeNodes = @NamedAttributeNode("indices"))
public class StockProduct extends Product implements Serializable{

	private static final long serialVersionUID = 1620238240796817290L;

	@JsonIgnore
    @ManyToMany(mappedBy="stocks", fetch=FetchType.LAZY)
    private Set<Index> indices = new LinkedHashSet<>();

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
				+ ", low()=" + getLow() + ", currency()=" + getCurrency()
				+ ", market()=" + getMarket() + "]";
	}
}