package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.ProductRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Product;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

@Service
public class ProductServiceImpl<T extends Product> implements ProductService<T> {
	
	@Autowired
	private ProductRepository<T> productRepository;
	
	@Autowired
	private StockProductRepository stockProductRepository;
	
	@Autowired
	private MarketRepository marketRepository;

	@Override
	public Page<T> get(String indexId, String exchangeId, MarketId marketId,
			String startWith, Specification<T> spec, Pageable pageable) {
		return null;
	}
}
