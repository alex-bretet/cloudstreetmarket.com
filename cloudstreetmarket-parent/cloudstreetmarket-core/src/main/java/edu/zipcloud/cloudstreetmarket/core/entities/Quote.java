/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.core.entities;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import static javax.persistence.InheritanceType.*;

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
public class Quote extends TableGeneratedId<Long>{

	private static final long serialVersionUID = -854929089039706306L;

	private Date date;
	
	@Column(precision = 10, scale = 5)
	private double open;
	
	@Column(name = "previous_close", precision = 10, scale = 5)
	private double previousClose;
	
	@Column(precision = 10, scale = 5)
	private double last;

	@Column(precision = 10, scale = 5)
	private double high;
	
	@Column(precision = 10, scale = 5)
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

	public boolean isExpired(int ttlInMinutes){
		Instant now = new Date().toInstant();
		LocalDateTime localNow = now.atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime localLastUpdate = DateUtils.addMinutes(lastUpdate, ttlInMinutes).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return localLastUpdate.isBefore(localNow);
	}

	@Override
	public String toString() {
		return "Quote [date=" + date + ", open=" + open + ", previousClose="
				+ previousClose + ", last=" + last + ", high=" + high
				+ ", low=" + low + ", lastUpdate=" + lastUpdate + ", id=" + id
				+ "]";
	}
}
