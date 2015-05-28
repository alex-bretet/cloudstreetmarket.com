package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="index_value")
public class Index extends AbstractId<String> implements Serializable{

	private static final long serialVersionUID = -2919348303931939346L;

	private String name;
	
	@Column(name="daily_latest_value")
	private BigDecimal dailyLatestValue;
	
	@Column(name="daily_latest_change")
	private BigDecimal dailyLatestChange;
	
	@Column(name="daily_latest_change_pc")
	private BigDecimal dailyLatestChangePercent;

	@Column(name = "previous_close")
	private BigDecimal previousClose;
	
	private BigDecimal high;
	
	private BigDecimal low;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "market_id", nullable=true)
	private Market market;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "stock_indices", joinColumns = { @JoinColumn(name = "index_code") },
			inverseJoinColumns = { @JoinColumn(name = "stock_code") })
	private Set<StockProduct> stocks = new LinkedHashSet<>();
	
	public Index(){}
	
	public Index(String indexId) {
		setId(indexId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getDailyLatestValue() {
		return dailyLatestValue;
	}

	public void setDailyLatestValue(BigDecimal dailyLatestValue) {
		this.dailyLatestValue = dailyLatestValue;
	}

	public BigDecimal getDailyLatestChange() {
		return dailyLatestChange;
	}

	public void setDailyLatestChange(BigDecimal dailyLatestChange) {
		this.dailyLatestChange = dailyLatestChange;
	}

	public BigDecimal getDailyLatestChangePercent() {
		return dailyLatestChangePercent;
	}

	public void setDailyLatestChangePercent(BigDecimal dailyLatestChangePercent) {
		this.dailyLatestChangePercent = dailyLatestChangePercent;
	}

	public BigDecimal getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(BigDecimal previousClose) {
		this.previousClose = previousClose;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Index other = (Index) obj;
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

	//Avoid fetching lazy collections at this stage (session may be closed)
	@Override
	public String toString() {
		return "Index [id="+getId().toString()+", name=" + name + ", dailyLatestValue=" + dailyLatestValue
				+ ", dailyLatestChange=" + dailyLatestChange
				+ ", dailyLatestChangePercent=" + dailyLatestChangePercent
				+ ", previousClose=" + previousClose + ", high=" + high
				+ ", low=" + low + ", market=" + market + "]";
	}
}
