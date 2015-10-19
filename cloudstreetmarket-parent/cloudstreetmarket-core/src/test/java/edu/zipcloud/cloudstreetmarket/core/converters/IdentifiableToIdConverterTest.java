package edu.zipcloud.cloudstreetmarket.core.converters;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.zipcloud.cloudstreetmarket.core.entities.*;

public class IdentifiableToIdConverterTest {

	@Test
	public void canConvertChartStock(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(ChartStock.class);
		assertTrue(converter.canConvert(ChartStock.class));
	}
	
	@Test
	public void canConvertAction(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(Action.class);
		assertTrue(converter.canConvert(Action.class));
	}
	
	@Test
	public void canConvertChartIndex(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(ChartIndex.class);
		assertTrue(converter.canConvert(ChartIndex.class));
	}
	
	@Test
	public void canConvertAccountActivity(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(AccountActivity.class);
		assertTrue(converter.canConvert(AccountActivity.class));
	}
	
	@Test
	public void canConvertAuthority(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(Authority.class);
		assertTrue(converter.canConvert(Authority.class));
	}
	
	@Test
	public void canConvertCommentAction(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(CommentAction.class);
		assertTrue(converter.canConvert(CommentAction.class));
	}
	
	@Test
	public void canConvertCurrencyExchange(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(CurrencyExchange.class);
		assertTrue(converter.canConvert(CurrencyExchange.class));
	}
	
	@Test
	public void canConvertCurrencyExchangeQuote(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(CurrencyExchangeQuote.class);
		assertTrue(converter.canConvert(CurrencyExchangeQuote.class));
	}
	
	@Test
	public void canConvertExchange(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(Exchange.class);
		assertTrue(converter.canConvert(Exchange.class));
	}
	
	@Test
	public void canConvertIndex(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(Index.class);
		assertTrue(converter.canConvert(Index.class));
	}

	@Test
	public void canConvertIndexQuote(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(IndexQuote.class);
		assertTrue(converter.canConvert(IndexQuote.class));
	}
	
	@Test
	public void canConvertIndustry(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(Industry.class);
		assertTrue(converter.canConvert(Industry.class));
	}
	
	@Test
	public void canConvertLikeAction(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(LikeAction.class);
		assertTrue(converter.canConvert(LikeAction.class));
	}
	
	@Test
	public void canConvertMarket(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(Market.class);
		assertTrue(converter.canConvert(Market.class));
	}
	
	@Test
	public void canConvertProduct(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(Product.class);
		assertTrue(converter.canConvert(Product.class));
	}

	@Test
	public void canConvertQuote(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(Quote.class);
		assertTrue(converter.canConvert(Quote.class));
	}
	
	@Test
	public void canConvertSocialAction(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(SocialAction.class);
		assertTrue(converter.canConvert(SocialAction.class));
	}
	
	@Test
	public void canConvertSocialEventAction(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(SocialEventAction.class);
		assertTrue(converter.canConvert(SocialEventAction.class));
	}

	@Test
	public void canConvertSocialUser(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(SocialUser.class);
		assertTrue(converter.canConvert(SocialUser.class));
	}
	
	@Test
	public void canConvertStockProduct(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(StockProduct.class);
		assertTrue(converter.canConvert(StockProduct.class));
	}
	
	@Test
	public void canConvertStockQuote(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(StockQuote.class);
		assertTrue(converter.canConvert(StockQuote.class));
	}

	@Test
	public void canConvertTransaction(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(Transaction.class);
		assertTrue(converter.canConvert(Transaction.class));
	}
	
	@Test
	public void canConvertUser(){
		IdentifiableToIdConverter converter = new IdentifiableToIdConverter(User.class);
		assertTrue(converter.canConvert(User.class));
	}
}
