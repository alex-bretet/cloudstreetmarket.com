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
