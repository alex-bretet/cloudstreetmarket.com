package edu.zipcloud.cloudstreetmarket.core.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;

import static javax.persistence.InheritanceType.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
public class Product extends AbstractId<String>{
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -802306391915956578L;

	protected String name;

	@Column(name="daily_latest_value")
	protected BigDecimal dailyLatestValue;
	
	@Column(name="daily_latest_change")
	protected BigDecimal dailyLatestChange;
	
	@Column(name="daily_latest_change_pc")
	protected BigDecimal dailyLatestChangePercent;

	@Column(name = "previous_close")
	protected BigDecimal previousClose;
	
	protected BigDecimal high;
	
	protected BigDecimal low;
	
	protected String currency;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "market_id")
	@JsonIgnore
	protected Market market;

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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
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
		return "Product [id=" + getId() + ", name=" + name + ", dailyLatestValue="
				+ dailyLatestValue + ", dailyLatestChange=" + dailyLatestChange
				+ ", dailyLatestChangePercent=" + dailyLatestChangePercent
				+ ", previousClose=" + previousClose + ", high=" + high
				+ ", low=" + low + ", currency=" + currency + ", market="
				+ market + "]";
	}
}
