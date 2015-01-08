package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class DailyMarketActivityDTO {
	
	String marketShortName;
	String marketId;
	Map<String, BigDecimal> values;
	Date dateSnapshot;
	
	public DailyMarketActivityDTO(String marketShortName, String marketId, Map<String, BigDecimal> values, Date dateSnapshot){
		this.marketShortName = marketShortName;
		this.marketId = marketId;
		this.values = values;
		this.dateSnapshot = dateSnapshot;
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
	
	public Map<String, BigDecimal> getValues() {
		return values;
	}
	
	public void setValues(Map<String, BigDecimal> values) {
		this.values = values;
	}
	
	public Date getDateSnapshot() {
		return dateSnapshot;
	}
	
	public void setDateSnapshot(Date dateSnapshot) {
		this.dateSnapshot = dateSnapshot;
	}
	
	public BigDecimal getMaxValue(){
		return Collections.max(values.values());
	}
	
	public BigDecimal getMinValue(){
		return Collections.min(values.values());
	}

}