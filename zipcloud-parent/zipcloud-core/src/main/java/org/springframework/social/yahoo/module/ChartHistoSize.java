package org.springframework.social.yahoo.module;

public enum ChartHistoSize {
	
	LARGE("l"), 
	MEDIUM("m"), 
	SMALL("s");
	
	private String tag;
	
	ChartHistoSize(String tag){
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
