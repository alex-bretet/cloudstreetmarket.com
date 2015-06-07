package org.springframework.social.yahoo.api;

import java.util.List;

import org.springframework.social.yahoo.module.YahooQuote;

public interface FinancialOperations {
	List<YahooQuote> getYahooQuotes(List<String> tickers, String accessToken) ;
	List<YahooQuote> getYahooHistos(String ticker, String token);
}
