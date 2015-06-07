package edu.zipcloud.cloudstreetmarket.core.services;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.daos.ExchangeRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.specifications.ProductSpecifications;

@Service
public class StockProductServiceImpl extends ProductServiceImpl<StockProduct> implements StockProductService {
	
	@Autowired
	private StockProductRepository stockProductRepository;
	
	@Autowired
	private IndexRepository indexRepository;
	
	@Autowired
	private ExchangeRepository exchangeRepository;
	
	@Autowired
	private MarketRepository marketRepository;
	
	@Override
	public Page<StockProduct> get(String indexId, String exchangeId, MarketId marketId, String startWith, Specification<StockProduct> spec, Pageable pageable) {
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
		
		if(marketId != null){
			Market market = marketRepository.findOne(marketId);
			if(market == null){
				throw new NoResultException("No result found for the market ID "+marketId+" !");
			}
			spec = Specifications.where(spec).and(new ProductSpecifications<StockProduct>().marketIdEquals(marketId));
		}
		
		if(!StringUtils.isEmpty(exchangeId)){
			spec = Specifications.where(spec).and(new ProductSpecifications<StockProduct>().exchangeIdEquals(exchangeId));
		}
		
		spec = Specifications.where(spec).and(new ProductSpecifications<StockProduct>().nameNotNull());

		return stockProductRepository.findAll(spec, pageable);
	}

	@Override
	public StockProduct get(String stockProductId) {
		return stockProductRepository.getOne(stockProductId);
	}
}
