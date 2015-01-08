package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.math.BigDecimal;

public class MarketOverviewDTO {

	private String marketShortName;
	private String marketId;
	private BigDecimal latestValue;
	private BigDecimal latestChange;
	
	public MarketOverviewDTO(String marketShortName, String marketId, BigDecimal latestValue, BigDecimal latestChange){
		this.marketShortName = marketShortName;
		this.marketId = marketId;
		this.latestValue = latestValue;
		this.latestChange = latestChange;
	}
	
	public String getMarketShortName() {
		return marketShortName;
	}
	
	public void setMarketShortName(String marketShortName) {
		this.marketShortName = marketShortName;
	}
	
	public String getMarketId() {
		return marketId;
	}
	
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	
	public BigDecimal getLatestValue() {
		return latestValue;
	}
	
	public void setLatestValue(BigDecimal latestValue) {
		this.latestValue = latestValue;
	}
	
	public BigDecimal getLatestChange() {
		return latestChange;
	}
	
	public void setLatestChange(BigDecimal latestChange) {
		this.latestChange = latestChange;
	}
	
}