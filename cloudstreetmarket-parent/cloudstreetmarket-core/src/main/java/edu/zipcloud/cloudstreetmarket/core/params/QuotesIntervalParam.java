package edu.zipcloud.cloudstreetmarket.core.params;

import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

public class QuotesIntervalParam {
	QuotesInterval value;
	
	public QuotesIntervalParam(String arg){
		this.value = QuotesInterval.valueOf(arg);
	}

	public QuotesInterval getValue() {
		return value;
	}
	
}
