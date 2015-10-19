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

import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.services.ExchangeService;

@RunWith(MockitoJUnitRunner.class)
public class YahooQuoteToIndexConverterTest {
	
	@InjectMocks
    private YahooQuoteToIndexConverter converter;
	
	@Mock
	private IndexRepository IndexRepository;
	
	@Mock
	private ExchangeService exchangeService;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	private static String NAME_VALUE = "FB";
	private static String EXCHANGE_VALUE = "ASX";
	private static String ID_VALUE = "WHATEVER_ID";
	private static double HIGH_VALUE = 12;
	private static double LAST_VALUE = 13;
	private static double LAST_CHANGE_VALUE = 14;
	private static double LAST_CHANGE_PERCENT_VALUE = 15;
	private static double LOW_VALUE = 16;
	private static double OPEN_VALUE = 17;
	private static double PREVIOUS_CLOSE_VALUE = 18;
	
	@Test
	public void essentialDataTransfered(){
		when(IndexRepository.findOne(any(String.class))).thenReturn(new Index(ID_VALUE));
		when(exchangeService.get(EXCHANGE_VALUE)).thenReturn(new Exchange(EXCHANGE_VALUE));
		validateResult(converter.convert(buildYahooQuoteInstance()));
		verify(IndexRepository, times(1)).findOne(any(String.class));
		verify(exchangeService, times(1)).get(any(String.class));
	}
	
	@Test
	public void producesNewIndexIfNoneFound(){
		when(IndexRepository.findOne(any(String.class))).thenReturn(null);
		when(exchangeService.get(EXCHANGE_VALUE)).thenReturn(new Exchange(EXCHANGE_VALUE));
		validateResult(converter.convert(buildYahooQuoteInstance()));
		verify(IndexRepository, times(1)).findOne(any(String.class));
		verify(exchangeService, times(1)).get(any(String.class));
	}
	
	private static YahooQuote buildYahooQuoteInstance(){
		return new YahooQuote.Builder()
			.withId(ID_VALUE)
			.withName(NAME_VALUE)
			.withExchange(EXCHANGE_VALUE)
			.withHigh(HIGH_VALUE)
			.withLow(LOW_VALUE)
			.withLast(LAST_VALUE)
			.withLastChange(LAST_CHANGE_VALUE)
			.withLastChangePercent(LAST_CHANGE_PERCENT_VALUE)
			.withPreviousClose(PREVIOUS_CLOSE_VALUE)
			.withOpen(OPEN_VALUE)
			.build();
	}
	
	private void validateResult(Index index){
		assertEquals(ID_VALUE, index.getId());
		assertEquals(NAME_VALUE, index.getName());
		assertEquals(BigDecimal.valueOf(HIGH_VALUE), index.getHigh());
		assertEquals(BigDecimal.valueOf(LOW_VALUE), index.getLow());
		assertEquals(BigDecimal.valueOf(LAST_VALUE), index.getDailyLatestValue());
		assertEquals(BigDecimal.valueOf(LAST_CHANGE_VALUE), index.getDailyLatestChange());
		assertEquals(BigDecimal.valueOf(LAST_CHANGE_PERCENT_VALUE), index.getDailyLatestChangePercent());
		assertEquals(BigDecimal.valueOf(PREVIOUS_CLOSE_VALUE), index.getPreviousClose());
		assertEquals(BigDecimal.valueOf(OPEN_VALUE), index.getOpen());
		assertEquals(EXCHANGE_VALUE, index.getExchange().getId());
		assertNotNull(index.getComponents());
	}
}
