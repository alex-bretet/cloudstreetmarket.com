package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import edu.zipcloud.cloudstreetmarket.core.dtos.ProductOverviewDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.Product;

public interface IProductService<T extends Product> {
	
	Page<ProductOverviewDTO> getProductsOverview(String startWith, Specification<T> spec, Pageable pageable);
	
}
