package edu.zipcloud.cloudstreetmarket.core.services;

import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;

public interface StockQuoteService extends QuoteService<StockQuote> {
	StockQuote get(Long stockQuoteId);
}
