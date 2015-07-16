package edu.zipcloud.cloudstreetmarket.core.dtos;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("wallet-item")
public class WalletItemDTO {
	
	private String symbol;
	private String companyName;
	private String exchange;
	private int quantity;
	private double avgCostPerShare;
	private double latestPrice;
	private double latestChangePercent;
	private double bookCost;
	private double valuation;
	private double profit;
	private double profitPercent;
	private String currency;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getAvgCostPerShare() {
		return avgCostPerShare;
	}
	public void setAvgCostPerShare(double avgCostPerShare) {
		this.avgCostPerShare = avgCostPerShare;
	}
	public double getLatestPrice() {
		return latestPrice;
	}
	public void setLatestPrice(double latestPrice) {
		this.latestPrice = latestPrice;
	}
	public double getBookCost() {
		return bookCost;
	}
	public void setBookCost(double bookCost) {
		this.bookCost = bookCost;
	}
	public double getValuation() {
		return valuation;
	}
	public void setValuation(double valuation) {
		this.valuation = valuation;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getProfitPercent() {
		return profitPercent;
	}
	public void setProfitPercent(double profitPercent) {
		this.profitPercent = profitPercent;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getLatestChangePercent() {
		return latestChangePercent;
	}
	public void setLatestChangePercent(double latestChangePercent) {
		this.latestChangePercent = latestChangePercent;
	}
}
