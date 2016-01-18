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

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.Transaction.Builder;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

@Entity
@Table(name="currency_exchange")
@XStreamAlias("currency_exchange")
public class CurrencyExchange extends ProvidedId<String> {
	
	private static final long serialVersionUID = 3197924065214694462L;

	protected String name;

	@Column(name="daily_latest_value", precision = 10, scale = 5)
	protected BigDecimal dailyLatestValue;

	@Column(name="daily_latest_change", precision = 10, scale = 5)
	protected BigDecimal dailyLatestChange;

	@Column(name="daily_latest_change_pc", precision = 10, scale = 5)
	protected BigDecimal dailyLatestChangePercent;

	@Column(name = "previous_close", precision = 10, scale = 5)
	protected BigDecimal previousClose;

	@Column(precision = 10, scale = 5)
	protected BigDecimal open;
	
	@Column(precision = 10, scale = 5)
	protected BigDecimal bid;

	@Column(precision = 10, scale = 5)
	protected BigDecimal ask;

	@Column(name="last_update", insertable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastUpdate;

	public CurrencyExchange(){}
	
	public CurrencyExchange(String indexId) {
		setId(indexId);
	}
	
	public CurrencyExchange(String indexId, String name, BigDecimal dailyLatestValue, BigDecimal dailyLatestChange, BigDecimal dailyLatestChangePercent, BigDecimal bid, BigDecimal ask, BigDecimal open, BigDecimal previousClose) {
		setId(indexId);
		this.name = name;
		this.dailyLatestValue = dailyLatestValue;
		this.dailyLatestChange = dailyLatestChange;
		this.dailyLatestChangePercent = dailyLatestChangePercent;
		this.previousClose = previousClose;
		this.bid = bid;
		this.ask = ask;
		this.open = open;
		this.previousClose = previousClose;
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
	
	public static class Builder extends CurrencyExchange {

		private static final long serialVersionUID = -7449245034377241127L;
		
		public Builder withId(String id){
			this.id = id;
			return this;
		}
		
		public Builder withDailyLatestValue(BigDecimal dailyLatestValue){
			this.dailyLatestValue = dailyLatestValue;
			return this;
		}
		
        public CurrencyExchange build() {
            return new CurrencyExchange(id, name, dailyLatestValue, dailyLatestChange, dailyLatestChangePercent, bid,  ask, open, previousClose);
        }
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
