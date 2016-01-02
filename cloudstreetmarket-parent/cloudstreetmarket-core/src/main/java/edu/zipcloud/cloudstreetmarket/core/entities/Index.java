package edu.zipcloud.cloudstreetmarket.core.entities;

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableSerializer;
import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableToIdConverter;

@Entity
@Table(name="index_value")
@XStreamAlias("index")
public class Index extends ProvidedId<String> {

	private String name;

	@Column(name="daily_latest_value")
	private BigDecimal dailyLatestValue;

	@Column(name="daily_latest_change")
	private BigDecimal dailyLatestChange;

	@Column(name="daily_latest_change_pc")
	private BigDecimal dailyLatestChangePercent;

	@Column(name = "previous_close")
	private BigDecimal previousClose;

	private BigDecimal open;
	
	private BigDecimal high;

	private BigDecimal low;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("exchangeId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("exchangeId")
    private Exchange exchange;

	@JsonIgnore
	@XStreamOmitField
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "stock_indices", joinColumns = { @JoinColumn(name = "index_code") },
			inverseJoinColumns = { @JoinColumn(name = "stock_code") })
	private Set<StockProduct> components = new LinkedHashSet<>();

	@Column(name="last_update", insertable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

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

	public Set<StockProduct> getComponents() {
		return components;
	}

	public void setComponents(Set<StockProduct> components) {
		this.components = components;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Exchange getExchange() {
		return exchange;
	}

	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	@Override
	public String toString() {
		return "Index [name=" + name + ", dailyLatestValue=" + dailyLatestValue
				+ ", dailyLatestChange=" + dailyLatestChange
				+ ", dailyLatestChangePercent=" + dailyLatestChangePercent
				+ ", previousClose=" + previousClose + ", open=" + open
				+ ", high=" + high + ", low=" + low + ", exchange=" + exchange
				+ ", lastUpdate=" + lastUpdate + ", id=" + id + "]";
	}
}
