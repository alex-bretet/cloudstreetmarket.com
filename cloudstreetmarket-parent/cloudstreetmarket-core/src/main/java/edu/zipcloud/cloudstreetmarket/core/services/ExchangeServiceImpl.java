package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.daos.ExchangeRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

@Service
public class ExchangeServiceImpl implements ExchangeService {

	@Autowired
	private ExchangeRepository exchangeRepository;
	
	@Override
	public Exchange get(String exchangeId) {
		return exchangeRepository.findOne(exchangeId);
	}

	@Override
	public Page<Exchange> getSeveral(MarketId marketId, Pageable pageable) {
		return exchangeRepository.findByMarket(pageable);
	}
}
