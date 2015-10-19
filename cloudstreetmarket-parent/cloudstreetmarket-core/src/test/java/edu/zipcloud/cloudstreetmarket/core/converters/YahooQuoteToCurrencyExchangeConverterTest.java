package edu.zipcloud.cloudstreetmarket.core.converters;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.yahoo.module.YahooQuote;

import edu.zipcloud.cloudstreetmarket.core.daos.CurrencyExchangeRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;

@RunWith(MockitoJUnitRunner.class)
public class YahooQuoteToCurrencyExchangeConverterTest {
	
	@InjectMocks
    private YahooQuoteToCurrencyExchangeConverter converter;
	
	@Mock
	private CurrencyExchangeRepository currencyExchangeRepository;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	private static String NAME_VALUE = "USDGBP=X";
	private static String ID_VALUE = "WHATEVER_ID";
	private static double BID_VALUE = 10;
	private static double ASK_VALUE = 11;
	private static double LAST_VALUE = 12;
	private static double LAST_CHANGE_VALUE = 13;
	private static double LAST_CHANGE_PERCENT_VALUE = 14;
	private static double OPEN_VALUE = 17;
	private static double PREVIOUS_CLOSE_VALUE = 18;
	
	@Test
	public void essentialDataTransfered(){
		when(currencyExchangeRepository.findOne(any(String.class))).thenReturn(new CurrencyExchange(ID_VALUE));
		validateResult(converter.convert(buildYahooQuoteInstance()));
		verify(currencyExchangeRepository, times(1)).findOne(any(String.class));
	}
	
	@Test
	public void producesNewCurrencyExchangeIfNoneFound(){
		when(currencyExchangeRepository.findOne(any(String.class))).thenReturn(null);
		validateResult(converter.convert(buildYahooQuoteInstance()));
		verify(currencyExchangeRepository, times(1)).findOne(any(String.class));
	}
	
	private static YahooQuote buildYahooQuoteInstance(){
			return new YahooQuote.Builder()
			.withId(ID_VALUE)
			.withName(NAME_VALUE)
			.withBid(BID_VALUE)
			.withAsk(ASK_VALUE)
			.withLast(LAST_VALUE)
			.withLastChange(LAST_CHANGE_VALUE)
			.withLastChangePercent(LAST_CHANGE_PERCENT_VALUE)
			.withPreviousClose(PREVIOUS_CLOSE_VALUE)
			.withOpen(OPEN_VALUE)
			.build();
	}
	
	private void validateResult(CurrencyExchange currencyExchange){
		assertEquals(ID_VALUE, currencyExchange.getId());
		assertEquals(NAME_VALUE, currencyExchange.getName());
		assertEquals(BigDecimal.valueOf(BID_VALUE), currencyExchange.getBid());
		assertEquals(BigDecimal.valueOf(ASK_VALUE), currencyExchange.getAsk());
		assertEquals(BigDecimal.valueOf(LAST_VALUE), currencyExchange.getDailyLatestValue());
		assertEquals(BigDecimal.valueOf(LAST_CHANGE_VALUE), currencyExchange.getDailyLatestChange());
		assertEquals(BigDecimal.valueOf(LAST_CHANGE_PERCENT_VALUE), currencyExchange.getDailyLatestChangePercent());
		assertEquals(BigDecimal.valueOf(PREVIOUS_CLOSE_VALUE), currencyExchange.getPreviousClose());
		assertEquals(BigDecimal.valueOf(OPEN_VALUE), currencyExchange.getOpen());
	}
}
