package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("historic")
public class HistoProductDTO {
	
	String productName;
	String code;
	Set<Map<String, BigDecimal>> values;
	Map<String, BigDecimal> mapValues = new HashMap<>();

	Date fromDate;

	Date toDate;
	
	public HistoProductDTO(String productName, String code, Map<String, BigDecimal> mapValues, Date fromDate, Date toDate){
		this.productName = productName;
		this.code = code;
		this.mapValues = mapValues;
		
		this.values = mapValues.entrySet().stream()
		    .map(entry -> {
		    	Map<String, BigDecimal> localMap = new HashMap<>();
		    	localMap.put(entry.getKey(), entry.getValue());
		    	return localMap;
			})
		    .collect(Collectors.toCollection(LinkedHashSet::new));

		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<Map<String, BigDecimal>> getValues() {
		return values;
	}

	public void setValues(Set<Map<String, BigDecimal>> values) {
		this.values = values;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getMaxValue(){
		return Collections.max(mapValues.values());
	}
	
	public BigDecimal getMinValue(){
		return Collections.min(mapValues.values());
	}
}