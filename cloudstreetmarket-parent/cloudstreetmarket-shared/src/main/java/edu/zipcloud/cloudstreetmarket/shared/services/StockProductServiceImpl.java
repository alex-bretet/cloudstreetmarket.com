package edu.zipcloud.cloudstreetmarket.shared.services;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.ChartStockRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartStock;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.services.ExchangeService;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.specifications.ChartSpecifications;
import edu.zipcloud.cloudstreetmarket.core.specifications.ProductSpecifications;

@Service
@Transactional(readOnly = true)
public class StockProductServiceImpl implements StockProductService {
	
	@Autowired
	protected StockProductRepository stockProductRepository;

	@Autowired
	protected ChartStockRepository chartStockRepository;

	@Autowired
	protected ExchangeService exchangeService;

    @Autowired
	protected Environment env;

	@Autowired
	protected SocialUserService usersConnectionRepository;
	
	@Autowired
	private IndexRepository indexRepository;

	@Autowired
	private MarketRepository marketRepository;
	
	@Override
	public Page<StockProduct> get(String indexId, String exchangeId, MarketId marketId, String startWith, Specification<StockProduct> spec, Pageable pageable, boolean validResults) {
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
		
		spec = Specifications.where(spec)
				.and(new ProductSpecifications<StockProduct>().nameNotNull())
				.and(new ProductSpecifications<StockProduct>().exchangeNotNull());

		if(validResults){
			spec = Specifications.where(spec)
					.and(new ProductSpecifications<StockProduct>().currencyNotNull())
					.and(new ProductSpecifications<StockProduct>().hasAPrice());
		}
		
		return stockProductRepository.findAll(spec, pageable);
	}

	@Override
	public StockProduct get(String stockProductId) {
		return stockProductRepository.findOne(stockProductId);
	}

	@Override
	public ChartStock getChartStock(StockProduct index, ChartType type,
			ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth,
			Integer intradayHeight) {
		
		Specification<ChartStock> spec = new ChartSpecifications<ChartStock>().typeEquals(type);
		
		if(type.equals(ChartType.HISTO)){
			if(histoSize != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartStock>().sizeEquals(histoSize));
			}
			if(histoAverage != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartStock>().histoMovingAverageEquals(histoAverage));
			}
			if(histoPeriod != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartStock>().histoTimeSpanEquals(histoPeriod));
			}
		}
		else{
			if(intradayWidth != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartStock>().intradayWidthEquals(intradayWidth));
			}
			if(intradayHeight != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartStock>().intradayHeightEquals(intradayHeight));
			}
		}
		
		spec = Specifications.where(spec).and(new ChartSpecifications<ChartStock>().indexEquals(index));
		return chartStockRepository.findAll(spec).stream().findFirst().orElse(null);
	}
}
