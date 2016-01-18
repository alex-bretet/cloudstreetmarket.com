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
package org.springframework.social.yahoo.module;

public class YahooQuote extends YahooObject {

    public YahooQuote(String id, String name, double open, double previousClose, double last, 
    		double lastChange, double lastChangePercent, double high, double low, double bid,
    		double ask, int volume, String exchange, String currency) {
    	this.id = id;
    	this.name = name;
    	this.open = open;
    	this.previousClose = previousClose;
    	this.last = last;
    	this.lastChange = lastChange;
    	this.lastChangePercent = lastChangePercent;
    	this.high = high;
    	this.low = low;
    	this.bid = bid;
    	this.ask = ask;
    	this.volume = volume;
    	this.exchange = exchange;
    	this.currency = currency;
	}
    
	private String id;
	private String name;
	
	private double open;
	private double previousClose;
	private double last;
	private double lastChange;
	private double lastChangePercent;
	
	private double high;
	private double low;
	private double bid;
	private double ask;
	private int volume;
	private String exchange;
	private String currency;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public double getLastChange() {
		return lastChange;
	}
	public void setLastChange(double lastChange) {
		this.lastChange = lastChange;
	}
	public double getLastChangePercent() {
		return lastChangePercent;
	}
	public void setLastChangePercent(double lastChangePercent) {
		this.lastChangePercent = lastChangePercent;
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
	public double getBid() {
		return bid;
	}
	public void setBid(double bid) {
		this.bid = bid;
	}
	public double getAsk() {
		return ask;
	}
	public void setAsk(double ask) {
		this.ask = ask;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public static class Builder {

    	private String id;
    	private String name;
    	private double open;
    	private double previousClose;
    	private double last;
    	private double lastChange;
    	private double lastChangePercent;
    	private double high;
    	private double low;
    	private double bid;
    	private double ask;
    	private int volume;
    	private String exchange;
    	private String currency;
    	
        public Builder withId(String id) {
        	this.id = id;
            return this;
        }
        
        public Builder withName(String name) {
        	this.name = name;
            return this;
        }
        
        public Builder withOpen(double open) {
        	this.open = open;
            return this;
        }
        
        public Builder withPreviousClose(double previousClose) {
        	this.previousClose = previousClose;
            return this;
        }
        
        public Builder withLast(double last) {
        	this.last = last;
            return this;
        }
        
        public Builder withLastChange(double lastChange) {
        	this.lastChange = lastChange;
            return this;
        }
        
        public Builder withLastChangePercent(double lastChangePercent) {
        	this.lastChangePercent = lastChangePercent;
            return this;
        }
        
        public Builder withHigh(double high) {
        	this.high = high;
            return this;
        }
        
        public Builder withLow(double low) {
        	this.low = low;
            return this;
        }
        
        public Builder withBid(double bid) {
        	this.bid = bid;
            return this;
        }
        
        public Builder withAsk(double ask) {
        	this.ask = ask;
            return this;
        }
        
        public Builder withVolume(int volume) {
        	this.volume = volume;
            return this;
        }
        
        public Builder withExchange(String exchange) {
        	this.exchange = exchange;
            return this;
        }
        
        public Builder withCurrency(String currency) {
        	this.currency = currency;
            return this;
        }

        public YahooQuote build() {
            return new YahooQuote(id, name, open, previousClose, last, lastChange, lastChangePercent, high, low, bid, ask, volume, exchange, currency);
        }
    }
	
	
	@Override
	public String toString() {
		return "Quote [id=" + id + ", name=" + name + ", open=" + open
				+ ", previousClose=" + previousClose + ", last=" + last
				+ ", lastChange=" + lastChange + ", lastChangePercent="
				+ lastChangePercent + ", high=" + high + ", low=" + low
				+ ", bid=" + bid + ", ask=" + ask + ", volume=" + volume
				+ ", exchange=" + exchange + ", currency=" + currency + "]";
	}

}
