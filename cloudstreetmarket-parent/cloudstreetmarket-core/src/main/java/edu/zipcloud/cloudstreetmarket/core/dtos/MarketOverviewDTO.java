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
package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("market")
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