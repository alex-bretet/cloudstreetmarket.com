package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.Index;

@XStreamAlias("index")
public class IndexOverviewDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code;
	private String name;
	private String market;
	private BigDecimal latestValue;
	private BigDecimal latestChange;
	private BigDecimal latestChangePercent;
	private BigDecimal prevClose;
	private BigDecimal high;
	private BigDecimal low;
	
	public IndexOverviewDTO(String name, String code, String market, BigDecimal latestValue, BigDecimal latestChange, BigDecimal latestChangePercent, BigDecimal prevClose, BigDecimal high, BigDecimal low){
		this.name = name;
		this.code = code;
		this.market = market;
		this.latestValue = latestValue;
		this.latestChange = latestChange;
		this.latestChangePercent = latestChangePercent;
		this.prevClose = prevClose;
		this.high = high;
		this.low = low;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public BigDecimal getLatestChangePercent() {
		return latestChangePercent;
	}

	public void setLatestChangePercent(BigDecimal latestChangePercent) {
		this.latestChangePercent = latestChangePercent;
	}

	public BigDecimal getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(BigDecimal prevClose) {
		this.prevClose = prevClose;
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
	
	public static IndexOverviewDTO build(Index index){
		return new IndexOverviewDTO(
					index.getName(), 
					index.getCode(),
					index.getMarket().getCode().name(),
					index.getDailyLatestValue(), 
					index.getDailyLatestChange(),
					index.getDailyLatestChangePercent(),
					index.getPreviousClose(),
					index.getHigh(),
					index.getLow()
		);
	}
	
}