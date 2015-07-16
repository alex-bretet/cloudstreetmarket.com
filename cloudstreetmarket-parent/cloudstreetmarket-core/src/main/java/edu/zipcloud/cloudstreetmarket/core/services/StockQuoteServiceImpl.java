package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockQuoteRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;

@Service
@Transactional(readOnly = true)
public class StockQuoteServiceImpl implements StockQuoteService {

	@Autowired
	private StockQuoteRepository stockQuoteRepository;
	
	@Autowired
	private StockProductRepository stockProductRepository;
	
	@Override
	public StockQuote get(Long stockQuoteId) {
		return stockQuoteRepository.findOne(stockQuoteId);
	}
	
	@Override
	public StockQuote hydrate(final StockQuote stockQuote) {
		
		if(stockQuote.getStock().getId() != null){
			stockQuote.setStock(stockProductRepository.findOne(stockQuote.getStock().getId()));
		}

		return stockQuote;
	}
}
