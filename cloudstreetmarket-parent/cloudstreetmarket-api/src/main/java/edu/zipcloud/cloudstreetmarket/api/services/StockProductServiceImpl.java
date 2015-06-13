package edu.zipcloud.cloudstreetmarket.api.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.yahoo.api.Yahoo2;
import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import edu.zipcloud.cloudstreetmarket.api.converters.StockProductResourceConverter;
import edu.zipcloud.cloudstreetmarket.api.resources.StockProductResource;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.converters.YahooQuoteToStockProductConverter;
import edu.zipcloud.cloudstreetmarket.core.daos.ChartIndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.ChartStockRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockQuoteRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartStock;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.specifications.ChartSpecifications;
import edu.zipcloud.cloudstreetmarket.core.specifications.ProductSpecifications;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;
import edu.zipcloud.core.util.DateUtil;

@Service
@Transactional
public class StockProductServiceImpl implements StockProductService {
	
	@Autowired
	private StockProductRepository stockProductRepository;
	
	@Autowired
	private StockQuoteRepository stockQuoteRepository;
	
	@Autowired
	private IndexRepository indexRepository;

	@Autowired
	private MarketRepository marketRepository;
	
	@Autowired
	private SocialUserService usersConnectionRepository;
	
	@Autowired
	private YahooQuoteToStockProductConverter yahooStockProductConverter;

	@Autowired
	private StockProductResourceConverter converter;
	
	@Autowired
	private ChartStockRepository chartStockRepository;
	
    @Autowired
	public Environment env;
	
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
		return stockProductRepository.findOne(stockProductId);
	}

	@Override
	public Page<StockProductResource> gather(String indexId, String exchangeId,
			MarketId marketId, String startWith,
			Specification<StockProduct> spec, Pageable pageable) {

		Page<StockProduct> stocks = get(indexId, exchangeId, marketId, startWith, spec, pageable);
		
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateStocksAndQuotesFromYahoo(stocks.getContent().stream().collect(Collectors.toSet()));
			
			return get(indexId, exchangeId, marketId, startWith, spec, pageable).map(converter);
		}
		
		return stocks.map(converter);
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
	@Transactional
	public StockProductResource gather(String stockProductId) {
		return converter.convert(gatherNonResource(stockProductId));
	}
	
	@Transactional
	private StockProduct gatherNonResource(String stockProductId) {
		StockProduct stock = stockProductRepository.findOne(stockProductId);
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateStocksAndQuotesFromYahoo(stock != null ? Sets.newHashSet(stock) : Sets.newHashSet(new StockProduct(stockProductId)));
			return stockProductRepository.findOne(stockProductId);
		}
		return stock;
	}

	@Override
	public ChartStock gather(String ticker, ChartType type,
			ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth,
			Integer intradayHeight) throws ResourceNotFoundException {
		Preconditions.checkNotNull(type, "ChartType must not be null!");
		
		StockProduct stock = gatherNonResource(ticker);

		ChartStock chartStock = getChartStock(stock, type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight);

		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateChartStockFromYahoo(stock, type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight);
			return getChartStock(stock, type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight);
		}
		else if(chartStock != null){
			return chartStock;
		}
		throw new ResourceNotFoundException();
	}

	private void updateChartStockFromYahoo(StockProduct stock, ChartType type, ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth, Integer intradayHeight) {
		
		Preconditions.checkNotNull(stock, "stock must not be null!");
		Preconditions.checkNotNull(type, "ChartType must not be null!");
		
		String guid = AuthenticationUtil.getPrincipal().getUsername();
		String token = usersConnectionRepository.getRegisteredSocialUser(guid).getAccessToken();
		Connection<Yahoo2> connection = usersConnectionRepository.createConnectionRepository(guid)
											.getPrimaryConnection(Yahoo2.class);
		
        if (connection != null) {
			Yahoo2 api = ((Yahoo2) connection.getApi());
			byte[] yahooChart = api.financialOperations().getYahooChart(stock.getId(), type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight, token);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm");
			LocalDateTime dateTime = LocalDateTime.now();
			String formattedDateTime = dateTime.format(formatter); // "1986-04-08_1230"
			String imageName = stock.getId().toLowerCase()+"_"+type.name().toLowerCase()+"_"+formattedDateTime+".png";
	    	String pathToYahooPicture = env.getProperty("pictures.yahoo.path").concat("\\").concat(imageName);
	    	
            try {
                Path newPath = Paths.get(pathToYahooPicture);
                Files.write(newPath, yahooChart, StandardOpenOption.CREATE);
            } catch (IOException e) {
                throw new Error("Storage of " + pathToYahooPicture+ " failed", e);
            }
            
            ChartStock chartStock = new ChartStock(stock, type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight, pathToYahooPicture);
            chartStockRepository.save(chartStock);
        }
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
