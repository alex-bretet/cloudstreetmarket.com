package edu.zipcloud.cloudstreetmarket.core.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@Table(name="currency_exchange")
@XStreamAlias("currency_exchange")
public class CurrencyExchange extends AbstractId<String> {
	
	private static final long serialVersionUID = 3197924065214694462L;

	private String name;

	@Column(name="daily_latest_value", precision = 10, scale = 5)
	private BigDecimal dailyLatestValue;

	@Column(name="daily_latest_change", precision = 10, scale = 5)
	private BigDecimal dailyLatestChange;

	@Column(name="daily_latest_change_pc", precision = 10, scale = 5)
	private BigDecimal dailyLatestChangePercent;

	@Column(name = "previous_close", precision = 10, scale = 5)
	private BigDecimal previousClose;

	@Column(precision = 10, scale = 5)
	private BigDecimal open;
	
	@Column(precision = 10, scale = 5)
	private BigDecimal bid;

	@Column(precision = 10, scale = 5)
	private BigDecimal ask;

	@Column(name="last_update", insertable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	public CurrencyExchange(){}
	
	public CurrencyExchange(String indexId) {
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

	public BigDecimal getBid() {
		return bid;
	}

	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}

	public BigDecimal getAsk() {
		return ask;
	}

	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	//Avoid fetching lazy collections (session may be closed depending upon where toString is called from)
	@Override
	public String toString() {
		return "Index [name=" + name + ", dailyLatestValue=" + dailyLatestValue
				+ ", dailyLatestChange=" + dailyLatestChange
				+ ", dailyLatestChangePercent=" + dailyLatestChangePercent
				+ ", previousClose=" + previousClose + ", open=" + open
				+ ", bid=" + bid + ", ask=" + ask + ", lastUpdate=" + lastUpdate + ", id=" + id + "]";
	}
}
