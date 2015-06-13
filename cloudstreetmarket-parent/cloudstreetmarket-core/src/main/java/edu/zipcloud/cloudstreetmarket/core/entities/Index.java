package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;
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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
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
@NamedEntityGraph(name="Index.exchange", attributeNodes={
	    @NamedAttributeNode("exchange")
	})
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
				+ ", open=" + open
				+ ", dailyLatestChangePercent=" + dailyLatestChangePercent
				+ ", previousClose=" + previousClose + ", high=" + high
				+ ", low=" + low + "]";
	}
}
