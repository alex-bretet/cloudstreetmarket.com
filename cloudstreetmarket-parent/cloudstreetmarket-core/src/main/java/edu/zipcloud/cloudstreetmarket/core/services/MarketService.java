package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.Market;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

public interface MarketService {
	Page<Market> getAll(Pageable pageable);
	Market get(MarketId marketCode);
}
