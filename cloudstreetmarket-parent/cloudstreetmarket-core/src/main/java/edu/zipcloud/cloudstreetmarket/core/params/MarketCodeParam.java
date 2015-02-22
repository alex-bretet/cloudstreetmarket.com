package edu.zipcloud.cloudstreetmarket.core.params;

import edu.zipcloud.cloudstreetmarket.core.enums.MarketCode;

public class MarketCodeParam{

	MarketCode value;
	
	public MarketCodeParam(String arg){
		this.value = MarketCode.valueOf(arg);
	}

	public MarketCode getValue() {
		return value;
	}
	
}
