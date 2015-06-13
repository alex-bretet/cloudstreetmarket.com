package org.springframework.social.yahoo.api;

import java.util.List;

import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;
import org.springframework.social.yahoo.module.YahooQuote;

public interface FinancialOperations {
	List<YahooQuote> getYahooQuotes(List<String> tickers, String accessToken) ;
	byte[] getYahooChart(String indexId, ChartType type, ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth, Integer intradayHeight, String token);
}
