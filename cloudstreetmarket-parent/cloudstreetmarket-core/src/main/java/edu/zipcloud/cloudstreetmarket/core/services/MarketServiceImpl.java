package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

@Service
public class MarketServiceImpl implements MarketService {

	@Autowired
	private MarketRepository marketRepository;

	@Override
	public Page<Market> getAll(Pageable pageable) {
		return marketRepository.findAll(pageable);
	}

	@Override
	public Market get(MarketId marketId) {
		Market market = marketRepository.findOne(marketId);
		if(market == null){
			throw new ResourceNotFoundException("No market has been found for the provided market ID: "+marketId);
		}
		return market;
	}
}
