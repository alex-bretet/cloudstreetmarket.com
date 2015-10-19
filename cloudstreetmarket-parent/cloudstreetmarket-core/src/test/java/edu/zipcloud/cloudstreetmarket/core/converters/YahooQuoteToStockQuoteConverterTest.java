package edu.zipcloud.cloudstreetmarket.core.converters;

import org.junit.Before;
import org.junit.Test;
import org.springframework.social.yahoo.module.YahooQuote;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import static org.junit.Assert.*;

public class YahooQuoteToStockQuoteConverterTest {
	
	private YahooQuoteToStockQuoteConverter converter;
	
	@Before
	public void setup(){
		converter = new YahooQuoteToStockQuoteConverter();
	}

	private static String CURRENCY_VALUE = "USD";
	private static String EXCHANGE_VALUE = "RANDOM";
	private static double HIGH_VALUE = 12;
	private static double LAST_CHANGE_VALUE = 14;
	private static double LAST_CHANGE_PERCENT_VALUE = 15;
	private static double LOW_VALUE = 16;
	private static double OPEN_VALUE = 17;
	private static double PREVIOUS_CLOSE_VALUE = 18;
	
	private static double DELTA_COMPARISON = 0.001;
	
	@Test
	public void essentialDataTransfered(){
		YahooQuote yahooQuote =  new YahooQuote.Builder()
										.withCurrency(CURRENCY_VALUE)
										.withExchange(EXCHANGE_VALUE)
										.withHigh(HIGH_VALUE)
										.withLastChange(LAST_CHANGE_VALUE)
										.withLastChangePercent(LAST_CHANGE_PERCENT_VALUE)
										.withLow(LOW_VALUE)
										.withOpen(OPEN_VALUE)
										.withPreviousClose(PREVIOUS_CLOSE_VALUE)
										.build();
		
		StockQuote stockQuote = converter.convert(yahooQuote);
		assertEquals(HIGH_VALUE, stockQuote.getHigh(), DELTA_COMPARISON);
		assertEquals(LOW_VALUE, stockQuote.getLow(), DELTA_COMPARISON);
		assertEquals(LAST_CHANGE_VALUE, stockQuote.getLastChange(), DELTA_COMPARISON);
		assertEquals(LAST_CHANGE_PERCENT_VALUE, stockQuote.getLastChangePercent(), DELTA_COMPARISON);
		assertEquals(PREVIOUS_CLOSE_VALUE, stockQuote.getPreviousClose(), DELTA_COMPARISON);
		assertEquals(OPEN_VALUE, stockQuote.getOpen(), DELTA_COMPARISON);
		assertEquals(CURRENCY_VALUE, stockQuote.getCurrency());
		assertEquals(EXCHANGE_VALUE, stockQuote.getExchange());
	}
}
