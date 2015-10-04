/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
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
