package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "historic_type")
@Table(name="historic")
public abstract class Historic {
	
	private static final long serialVersionUID = -802306391915956578L;

	@Id
	@GeneratedValue
	private int id;

	private double open;
	
	private double high;
	
	private double low;
	
	private double close;
	
	private double volume;
	
	@Column(name="adj_close")
	private double adjClose;

	@Column(name="change_percent")
	private double changePercent;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="from_date")
	private Date fromDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="to_date")
	private Date toDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name="interval")
	private QuotesInterval interval;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getAdjClose() {
		return adjClose;
	}

	public void setAdjClose(double adjClose) {
		this.adjClose = adjClose;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public QuotesInterval getInterval() {
		return interval;
	}

	public void setInterval(QuotesInterval interval) {
		this.interval = interval;
	}

	public double getChangePercent() {
		return changePercent;
	}

	public void setChangePercent(double changePercent) {
		this.changePercent = changePercent;
	}

}
