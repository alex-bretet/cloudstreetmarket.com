package org.springframework.social.yahoo.module;

import java.io.Serializable;

public enum ChartType implements Serializable{
	
	INTRADAY("t"), 
	HISTO("z");
	
	private String tag;
	
	ChartType(String tag){
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
