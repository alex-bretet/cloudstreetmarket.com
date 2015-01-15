package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.math.BigDecimal;

public class IndexOverviewDTO {

	private String code;
	private String name;
	private BigDecimal latestValue;
	private BigDecimal latestChange;
	
	public IndexOverviewDTO(String name, String code, BigDecimal latestValue, BigDecimal latestChange){
		this.code = code;
		this.name = name;
		this.latestValue = latestValue;
		this.latestChange = latestChange;
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
	
}