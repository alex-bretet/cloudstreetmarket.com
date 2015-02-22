package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.ProductRepository;
import edu.zipcloud.cloudstreetmarket.core.dtos.ProductOverviewDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.Product;
import edu.zipcloud.cloudstreetmarket.core.specifications.ProductSpecifications;

@Service
public class ProductServiceImpl<T extends Product> implements IProductService<T> {
	
	@Autowired
	private ProductRepository<T> productRepository;
	
	@Autowired
	private MarketRepository marketRepository;
	
	@Override
	public Page<ProductOverviewDTO> getProductsOverview(String startWith, Specification<T> spec, Pageable pageable) {
		
		if(StringUtils.isNotBlank(startWith)){
			spec = Specifications.where(spec).and(new ProductSpecifications<T>().nameStartsWith(startWith));
		}
		
		Page<T> products = productRepository.findAll(spec, pageable);
		List<ProductOverviewDTO> result = new LinkedList<>();
		for (T product : products) {
			result.add(ProductOverviewDTO.build(product));
		}
		return new PageImpl<>(result, pageable, products.getTotalElements());
	}
}
