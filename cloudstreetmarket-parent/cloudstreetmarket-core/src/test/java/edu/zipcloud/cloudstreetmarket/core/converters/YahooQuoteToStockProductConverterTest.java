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

import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.services.ExchangeService;

@RunWith(MockitoJUnitRunner.class)
public class YahooQuoteToStockProductConverterTest {
	
	@InjectMocks
    private YahooQuoteToStockProductConverter converter;
	
	@Mock
	private StockProductRepository stockProductRepository;
	
	@Mock
	private ExchangeService exchangeService;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	private static String CURRENCY_VALUE = "USD";
	private static String EXCHANGE_VALUE = "ASX";
	private static String ID_VALUE = "WHATEVER_ID";
	private static double HIGH_VALUE = 12;
	private static double LAST_CHANGE_VALUE = 14;
	private static double LAST_CHANGE_PERCENT_VALUE = 15;
	private static double LOW_VALUE = 16;
	private static double OPEN_VALUE = 17;
	private static double PREVIOUS_CLOSE_VALUE = 18;
	
	@Test
	public void essentialDataTransfered(){
		when(stockProductRepository.findOne(any(String.class))).thenReturn(new StockProduct(ID_VALUE));
		when(exchangeService.get(EXCHANGE_VALUE)).thenReturn(new Exchange(EXCHANGE_VALUE));
		validateResult(converter.convert(buildYahooQuoteInstance()));
		verify(stockProductRepository, times(1)).findOne(any(String.class));
		verify(exchangeService, times(1)).get(any(String.class));
	}
	
	@Test
	public void producesNewStockProductIfNoneFound(){
		when(stockProductRepository.findOne(any(String.class))).thenReturn(null);
		when(exchangeService.get(EXCHANGE_VALUE)).thenReturn(new Exchange(EXCHANGE_VALUE));
		validateResult(converter.convert(buildYahooQuoteInstance()));
		verify(stockProductRepository, times(1)).findOne(any(String.class));
		verify(exchangeService, times(1)).get(any(String.class));
	}
	
	private static YahooQuote buildYahooQuoteInstance(){
		return new YahooQuote.Builder()
			.withId(ID_VALUE)
			.withCurrency(CURRENCY_VALUE)
			.withExchange(EXCHANGE_VALUE)
			.withHigh(HIGH_VALUE)
			.withLow(LOW_VALUE)
			.withLastChange(LAST_CHANGE_VALUE)
			.withLastChangePercent(LAST_CHANGE_PERCENT_VALUE)
			.withPreviousClose(PREVIOUS_CLOSE_VALUE)
			.withOpen(OPEN_VALUE)
			.build();
	}
	
	private void validateResult(StockProduct stockProduct){
		assertEquals(ID_VALUE, stockProduct.getId());
		assertEquals(BigDecimal.valueOf(HIGH_VALUE), stockProduct.getHigh());
		assertEquals(BigDecimal.valueOf(LOW_VALUE), stockProduct.getLow());
		assertEquals(BigDecimal.valueOf(LAST_CHANGE_VALUE), stockProduct.getDailyLatestChange());
		assertEquals(BigDecimal.valueOf(LAST_CHANGE_PERCENT_VALUE), stockProduct.getDailyLatestChangePercent());
		assertEquals(BigDecimal.valueOf(PREVIOUS_CLOSE_VALUE), stockProduct.getPreviousClose());
		assertEquals(BigDecimal.valueOf(OPEN_VALUE), stockProduct.getOpen());
		assertEquals(CURRENCY_VALUE, stockProduct.getCurrency());
		assertEquals(EXCHANGE_VALUE, stockProduct.getExchange().getId());
	}
}
