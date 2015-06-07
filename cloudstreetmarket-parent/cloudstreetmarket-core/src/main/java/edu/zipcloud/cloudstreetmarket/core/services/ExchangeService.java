package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

public interface ExchangeService {
	Page<Exchange> getSeveral(MarketId marketId, Pageable pageable);
	Exchange get(String exchangeId);
}
