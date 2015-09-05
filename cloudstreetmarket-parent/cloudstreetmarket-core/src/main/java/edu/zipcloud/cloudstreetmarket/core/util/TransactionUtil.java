package edu.zipcloud.cloudstreetmarket.core.util;
import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;
import edu.zipcloud.cloudstreetmarket.core.entities.Quote;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

public class TransactionUtil {
	
	public static BigDecimal getPriceInUserCurrency(Quote quote, UserActivityType transactionType, int quantity, User user, @Nullable CurrencyExchange currencyExchange){
		Preconditions.checkNotNull(transactionType);
		Preconditions.checkNotNull(user);
		Preconditions.checkNotNull(quote);
		Preconditions.checkArgument(quote instanceof StockQuote, "Quote type not supported yet");
		Preconditions.checkArgument(transactionType.equals(UserActivityType.BUY) || transactionType.equals(UserActivityType.SELL));
		
		StockQuote stockQuote = (StockQuote) quote;

		BigDecimal priceInUserCurrency;
		if(user.getCurrency().equals(stockQuote.getSupportedCurrency())){
			priceInUserCurrency = BigDecimal.valueOf(
					(transactionType.equals(transactionType.BUY)? stockQuote.getBid() : stockQuote.getAsk()) * quantity);
		}
		else{
			Preconditions.checkNotNull(currencyExchange);
			Preconditions.checkArgument(currencyExchange.getDailyLatestValue() != null);
			Preconditions.checkArgument(currencyExchange.getDailyLatestValue().doubleValue() > 0);
			
			priceInUserCurrency = currencyExchange.getDailyLatestValue().multiply(BigDecimal.valueOf(
					(transactionType.equals(transactionType.BUY)? stockQuote.getBid() : stockQuote.getAsk()) * quantity));
		}
		return priceInUserCurrency;
	}
	
	public static BigDecimal getPrice(Quote quote, UserActivityType transactionType, int quantity){
		Preconditions.checkNotNull(transactionType);
		Preconditions.checkNotNull(quote);
		Preconditions.checkArgument(quote instanceof StockQuote, "Quote type not supported yet");
		Preconditions.checkArgument(transactionType.equals(UserActivityType.BUY) || transactionType.equals(UserActivityType.SELL));
		
		StockQuote stockQuote = (StockQuote) quote;

		BigDecimal priceInUserCurrency = BigDecimal.valueOf(
					(transactionType.equals(transactionType.BUY)? stockQuote.getBid() : stockQuote.getAsk()) * quantity);

		return priceInUserCurrency;
	}
}
