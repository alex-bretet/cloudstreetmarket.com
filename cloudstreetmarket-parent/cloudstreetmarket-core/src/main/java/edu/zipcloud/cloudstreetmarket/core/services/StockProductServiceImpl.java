package edu.zipcloud.cloudstreetmarket.core.services;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.specifications.ProductSpecifications;

@Service
public class StockProductServiceImpl extends ProductServiceImpl<StockProduct> implements StockProductService {
	
	@Autowired
	private StockProductRepository stockProductRepository;
	
	@Autowired
	private IndexRepository indexRepository;
	
	@Override
	public Page<StockProduct> get(String indexId, String startWith, Specification<StockProduct> spec, Pageable pageable) {

		if(!StringUtils.isEmpty(indexId)){
			Index index = indexRepository.findOne(indexId);
			if(index == null){
				throw new NoResultException("No result found for the index ID "+indexId+" !");
			}
			return stockProductRepository.findByIndices(index, pageable);
		}
		
		if(!StringUtils.isEmpty(startWith)){
			spec = Specifications.where(spec).and(new ProductSpecifications<StockProduct>().nameStartsWith(startWith));
		}
		
		return stockProductRepository.findAll(spec, pageable);
	}

	@Override
	public StockProduct get(String stockProductId) {
		StockProduct stockProduct = stockProductRepository.findOne(stockProductId);
		if(stockProduct == null){
			throw new ResourceNotFoundException("No stock has been found for the provided ID: "+stockProductId);
		}
		return stockProduct;
	}
}
