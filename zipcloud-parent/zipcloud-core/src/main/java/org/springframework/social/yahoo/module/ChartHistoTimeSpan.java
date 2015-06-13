package org.springframework.social.yahoo.module;

public enum ChartHistoTimeSpan {
	
	c1D("1d"), 
	c5D("5d"), 
	c3M("3m"), 
	c6M("6m"), 
	c1Y("1y"), 
	c2Y("2y"), 
	c5Y("5y"), 
	cMax("my");
	
	private String tag;
	
	ChartHistoTimeSpan(String tag){
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
