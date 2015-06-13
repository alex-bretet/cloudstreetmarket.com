package org.springframework.social.yahoo.module;

public enum ChartHistoMovingAverage {
	
	m5("m5"), 
	m10("m10"), 
	m20("m20"), 
	m50("m50"), 
	m100("m100");
	
	private String tag;
	
	ChartHistoMovingAverage(String tag){
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
