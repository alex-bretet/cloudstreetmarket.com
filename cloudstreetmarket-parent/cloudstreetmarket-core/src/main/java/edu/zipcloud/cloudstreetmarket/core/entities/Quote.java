package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import static javax.persistence.InheritanceType.*;

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
public class Quote extends AbstractTableGeneratedId<Long>{

	private Date date;
	
	private double open;
	
	@Column(name = "previous_close")
	private double previousClose;
	
	private double last;

	private double high;
	
	private double low;

	@Column(name="last_update", insertable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	@OneToMany(mappedBy = "quote")
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("date desc")
	private Set<Transaction> transactions = new LinkedHashSet<Transaction>();
	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}

	public double getLast() {
		return last;
	}

	public void setLast(double last) {
		this.last = last;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	//Avoid fetching lazy collections at this stage (session may be closed)
	@Override
	public String toString() {
		return "Quote [date=" + date + ", open=" + open + ", previousClose="
				+ previousClose + ", last=" + last + ", high=" + high
				+ ", low=" + low + ", lastUpdate=" + lastUpdate + ", id=" + id
				+ "]";
	}
}
