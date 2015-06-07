package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.stereotype.Repository;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;

@Repository
public interface StockQuoteRepository extends QuoteRepository<StockQuote> {

}