package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

public interface StockProductService extends ProductService<StockProduct> {
	Page<StockProduct> get(String indexId, String startWith, Specification<StockProduct> spec, Pageable pageable);
	StockProduct get(String stockProductId);
}
