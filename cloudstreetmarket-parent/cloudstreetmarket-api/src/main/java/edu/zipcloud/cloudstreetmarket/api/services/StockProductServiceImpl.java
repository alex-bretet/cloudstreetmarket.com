package edu.zipcloud.cloudstreetmarket.api.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.social.connect.Connection;
import org.springframework.social.yahoo.api.Yahoo2;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import com.google.common.collect.Sets;

import edu.zipcloud.cloudstreetmarket.core.services.ProductServiceImpl;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.converters.YahooQuoteToStockProductConverter;
import edu.zipcloud.cloudstreetmarket.core.daos.ExchangeRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockQuoteRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.specifications.ProductSpecifications;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;
import edu.zipcloud.core.util.DateUtil;

@Service
public class StockProductServiceImpl extends ProductServiceImpl<StockProduct> implements StockProductService {
	
	@Autowired
	private StockProductRepository stockProductRepository;
	
	@Autowired
	private StockQuoteRepository stockQuoteRepository;
	
	@Autowired
	private IndexRepository indexRepository;
	
	@Autowired
	private ExchangeRepository exchangeRepository;
	
	@Autowired
	private MarketRepository marketRepository;
	
	@Autowired
	private SocialUserService usersConnectionRepository;
	
	@Autowired
	private YahooQuoteToStockProductConverter yahooStockProductConverter;

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

	@Override
	public Page<StockProduct> gather(String indexId, String exchangeId,
			MarketId marketId, String startWith,
			Specification<StockProduct> spec, Pageable pageable) {

		Page<StockProduct> stocks = get(indexId, exchangeId, marketId, startWith, spec, pageable);
		
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateStocksAndQuotesFromYahoo(stocks.getContent().stream().collect(Collectors.toSet()));
			
			return get(indexId, exchangeId, marketId, startWith, spec, pageable);
		}
		
		return stocks;
	}

	private void updateStocksAndQuotesFromYahoo(Set<StockProduct> askedContent) {
		if(askedContent.isEmpty()){
			return;
		}
		
		Set<StockProduct> recentlyUpdated = askedContent.stream()
			.filter(t -> t.getLastUpdate() != null && DateUtil.isRecent(t.getLastUpdate(), 1))
			.collect(Collectors.toSet());

		if(askedContent.size() != recentlyUpdated.size()){
			
			String guid = AuthenticationUtil.getPrincipal().getUsername();
			String token = usersConnectionRepository.getRegisteredSocialUser(guid).getAccessToken();
			Connection<Yahoo2> connection = usersConnectionRepository.createConnectionRepository(guid)
												.getPrimaryConnection(Yahoo2.class);
			
	        if (connection != null) {
				askedContent.removeAll(recentlyUpdated);
			
				List<String> updatableTickers = askedContent.stream()
						.map(StockProduct::getId)
						.collect(Collectors.toList());
				
				Yahoo2 api = ((Yahoo2) connection.getApi());
				List<YahooQuote> yahooQuotes = null;
				
	
				yahooQuotes = api.financialOperations().getYahooQuotes(updatableTickers, token);

				Set<StockProduct> upToDateStocks = yahooQuotes.stream()
						.map(t -> yahooStockProductConverter.convert(t))
						.collect(Collectors.toSet());
				
				final Map<String, StockProduct> persistedStocks = stockProductRepository.save(upToDateStocks).stream()
						.collect(Collectors.toMap(StockProduct::getId, Function.identity()));

				Set<StockQuote> updatableQuotes = yahooQuotes.stream()
						.map(sq -> new StockQuote(sq, persistedStocks.get(sq.getId())))
						.collect(Collectors.toSet());

				stockQuoteRepository.save(updatableQuotes);
	        }
		}
	}

	@Override
	public StockProduct gather(String stockProductId) {
		StockProduct stock = stockProductRepository.getOne(stockProductId);
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateStocksAndQuotesFromYahoo(Sets.newHashSet(stock));
			return stockProductRepository.getOne(stockProductId);
		}
		return stock;
	}
}
