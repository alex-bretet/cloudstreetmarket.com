package edu.zipcloud.cloudstreetmarket.core.services;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.ProductRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Product;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.specifications.ProductSpecifications;

@Service
public class ProductServiceImpl<T extends Product> implements ProductService<T> {
	
	@Autowired
	private ProductRepository<T> productRepository;
	
	@Autowired
	private StockProductRepository stockProductRepository;
	
	@Autowired
	private MarketRepository marketRepository;

	@Override
	public Page<T> get(MarketId marketId, String startWith, Specification<T> spec, Pageable pageable) {
		
		if(!StringUtils.isEmpty(startWith)){
			spec = Specifications.where(spec).and(new ProductSpecifications<T>().nameStartsWith(startWith));
		}
		
		if(marketId != null){
			spec = Specifications.where(spec).and(new ProductSpecifications<T>().marketIdEquals(marketId));
		}
		
		return productRepository.findAll(spec, pageable);
	}
}
