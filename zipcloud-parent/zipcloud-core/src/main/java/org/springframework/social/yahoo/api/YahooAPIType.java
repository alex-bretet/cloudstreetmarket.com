package org.springframework.social.yahoo.api;

public enum YahooAPIType {
	SOCIAL("https://social.yahooapis.com/v1/user/%s"),
	FINANCIAL("http://finance.yahoo.com/d/"),
	FINANCIAL_CHARTS_HISTO("http://chart.finance.yahoo.com/z"),
	FINANCIAL_CHARTS_INTRA("http://chart.finance.yahoo.com/t");
	
	private String url;
	
	YahooAPIType(String url){
		this.url = url;
	}
	
	public String getBaseUrl(){
		return url;
	}
}
