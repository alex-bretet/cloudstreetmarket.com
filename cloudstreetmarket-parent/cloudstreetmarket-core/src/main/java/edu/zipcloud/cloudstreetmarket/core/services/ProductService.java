package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import edu.zipcloud.cloudstreetmarket.core.entities.Product;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

public interface ProductService<T extends Product> {
	Page<T> get(String indexId, String exchangeId,MarketId marketId, String startWith, Specification<T> spec, Pageable pageable);
}
