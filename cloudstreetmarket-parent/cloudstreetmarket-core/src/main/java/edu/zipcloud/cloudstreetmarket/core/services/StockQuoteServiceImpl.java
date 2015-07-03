package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.StockQuoteRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;

@Service
@Transactional
public class StockQuoteServiceImpl implements StockQuoteService {

	@Autowired
	private StockQuoteRepository stockQuoteRepository;

	@Override
	public StockQuote get(Long stockQuoteId) {
		return stockQuoteRepository.findOne(stockQuoteId);
	}
}
