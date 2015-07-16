package edu.zipcloud.cloudstreetmarket.core.entities;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
public class Product extends AbstractId<String>{
	
	protected String name;

	@Column(name="daily_latest_value")
	protected BigDecimal dailyLatestValue;
	
	@Column(name="daily_latest_change")
	protected BigDecimal dailyLatestChange;
	
	@Column(name="daily_latest_change_pc")
	protected BigDecimal dailyLatestChangePercent;

	@Column(name = "previous_close")
	protected BigDecimal previousClose;
	
	private BigDecimal open;
	
	protected BigDecimal high;
	
	protected BigDecimal low;
	
	protected String currency;

	@Column(name="last_update", insertable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	//Avoid fetching lazy collections at this stage (session may be closed)
	@Override
	public String toString() {
		return "Product [name=" + name + ", dailyLatestValue="
				+ dailyLatestValue + ", dailyLatestChange=" + dailyLatestChange
				+ ", dailyLatestChangePercent=" + dailyLatestChangePercent
				+ ", previousClose=" + previousClose + ", open=" + open
				+ ", high=" + high + ", low=" + low + ", currency=" + currency
				+ ", lastUpdate=" + lastUpdate + ", id=" + id + "]";
	}
}
