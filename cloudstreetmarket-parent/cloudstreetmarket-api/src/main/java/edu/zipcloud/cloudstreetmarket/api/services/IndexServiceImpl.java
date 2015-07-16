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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
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

import edu.zipcloud.cloudstreetmarket.core.converters.YahooQuoteToIndexConverter;
import edu.zipcloud.cloudstreetmarket.core.daos.ChartIndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.ExchangeRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexQuoteRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.IndexQuote;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.specifications.ChartSpecifications;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;
import edu.zipcloud.core.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class IndexServiceImpl implements IndexService {

	@Autowired
	private MarketRepository marketRepository;
	
	@Autowired
	private ExchangeRepository exchangeRepository;
	
	@Autowired
	private ChartIndexRepository historicalIndexRepository;
	
	@Autowired
	private IndexRepository indexRepository;
	
	@Autowired
	private SocialUserService usersConnectionRepository;
	
	@Autowired
	private ConnectionRepository connectionRepository;
	
	@Autowired
	private YahooQuoteToIndexConverter yahooIndexConverter;
	
	@Autowired
	private IndexQuoteRepository indexQuoteRepository;
	
	@Autowired
	private ChartIndexRepository chartIndexRepository;

    @Autowired
	public Environment env;

	@Override
	public Page<Index> getIndices(String exchangeId, MarketId marketId, Pageable pageable) {
		if(!StringUtils.isEmpty(exchangeId)){
			return indexRepository.findByExchange(exchangeRepository.findOne(exchangeId), pageable);
		}
		else if(marketId!=null){
			return indexRepository.findByMarket(marketRepository.findOne(marketId), pageable);
		}
		return indexRepository.findAll(pageable);
	}

	@Override
	public Page<Index> getIndices(Pageable pageable) {
		return indexRepository.findAll(pageable);
	}

	@Override
	public Index getIndex(String id) {
		Index index = indexRepository.findOne(id);
		if(index == null){
			throw new ResourceNotFoundException("No index has been found for the provided index ID: "+id);
		}
		return index;
	}

	@Override
	@Transactional
	public Page<Index> gather(String exchangeId, MarketId marketId, Pageable pageable) {
		
		Page<Index> indices = getIndices(exchangeId, marketId, pageable);
		
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateIndexAndQuotesFromYahoo(indices.getContent().stream().collect(Collectors.toSet()));
			return getIndices(exchangeId, marketId, pageable);
		}
		
		return indices;
	}

	@Override
	@Transactional
	public Index gather(String indexId) {
		Index index = indexRepository.findOne(indexId);
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateIndexAndQuotesFromYahoo(index != null ? Sets.newHashSet(index) : Sets.newHashSet(new Index(indexId)));
			return indexRepository.findOne(indexId);
		}
		return index;
	}

	private void updateIndexAndQuotesFromYahoo(Set<Index> askedContent) {
		
		Set<Index> recentlyUpdated = askedContent.stream()
					.filter(t -> t.getLastUpdate() != null && DateUtil.isRecent(t.getLastUpdate(), 1))
					.collect(Collectors.toSet());
		
		if(askedContent.size() != recentlyUpdated.size()){
			
			String guid = AuthenticationUtil.getPrincipal().getUsername();
			String token = usersConnectionRepository.getRegisteredSocialUser(guid).getAccessToken();
			Connection<Yahoo2> connection = connectionRepository.getPrimaryConnection(Yahoo2.class);

	        if (connection != null) {
				askedContent.removeAll(recentlyUpdated);
			
				List<String> updatableTickers = askedContent.stream()
						.map(Index::getId)
						.collect(Collectors.toList());
				
				List<YahooQuote> yahooQuotes = connection.getApi().financialOperations().getYahooQuotes(updatableTickers, token);

				Set<Index> upToDateIndex = yahooQuotes.stream()
						.map(t -> yahooIndexConverter.convert(t))
						.collect(Collectors.toSet());
				
				final Map<String, Index> persistedStocks = indexRepository.save(upToDateIndex).stream()
						.collect(Collectors.toMap(Index::getId, Function.identity()));

				Set<IndexQuote> updatableQuotes = yahooQuotes.stream()
						.map(sq -> new IndexQuote(sq, persistedStocks.get(sq.getId())))
						.collect(Collectors.toSet());

				indexQuoteRepository.save(updatableQuotes);
	        }
		}
	}

	@Override
	@Transactional
	public ChartIndex gather(String indexId, ChartType type,
			ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth,
			Integer intradayHeight) throws ResourceNotFoundException {
		
		Preconditions.checkNotNull(type, "ChartType must not be null!");
		
		Index index = gather(indexId);
		ChartIndex chartIndex = getChartIndex(index, type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight);
		Integer ttl = Integer.parseInt(env.getProperty("yahoo.graphs."+type.name().toLowerCase()+".ttl.minutes"));

		if(chartIndex!=null && !chartIndex.isExpired(ttl)){
			return chartIndex;
		}
		else if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateChartIndexFromYahoo(index, type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight);
			return getChartIndex(index, type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight);
		}
		else if(chartIndex != null){
			return chartIndex;
		}
		throw new ResourceNotFoundException();
	}
	
	private void updateChartIndexFromYahoo(Index index, ChartType type, ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth, Integer intradayHeight) {
		
		Preconditions.checkNotNull(index, "index must not be null!");
		Preconditions.checkNotNull(type, "ChartType must not be null!");
		
		String guid = AuthenticationUtil.getPrincipal().getUsername();
		String token = usersConnectionRepository.getRegisteredSocialUser(guid).getAccessToken();
        Connection<Yahoo2> connection = connectionRepository.getPrimaryConnection(Yahoo2.class);

        if (connection != null) {
			byte[] yahooChart = connection.getApi().financialOperations().getYahooChart(index.getId(), type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight, token);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm");
			LocalDateTime dateTime = LocalDateTime.now();
			String formattedDateTime = dateTime.format(formatter); // "1986-04-08_1230"
			String imageName = index.getId().toLowerCase()+"_"+type.name().toLowerCase()+"_"+formattedDateTime+".png";
	    	String pathToYahooPicture = env.getProperty("user.home").concat(env.getProperty("pictures.yahoo.path")).concat("\\").concat(imageName);
	    	
            try {
                Path newPath = Paths.get(pathToYahooPicture);
                Files.write(newPath, yahooChart, StandardOpenOption.CREATE);
            } catch (IOException e) {
                throw new Error("Storage of " + pathToYahooPicture+ " failed", e);
            }
            
            ChartIndex chartIndex = new ChartIndex(index, type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight, pathToYahooPicture);
            chartIndexRepository.save(chartIndex);
        }
	}
	
	@Override
	public ChartIndex getChartIndex(Index index, ChartType type,
			ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth,
			Integer intradayHeight) {
		
		Specification<ChartIndex> spec = new ChartSpecifications<ChartIndex>().typeEquals(type);
		
		if(type.equals(ChartType.HISTO)){
			if(histoSize != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().sizeEquals(histoSize));
			}
			if(histoAverage != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().histoMovingAverageEquals(histoAverage));
			}
			if(histoPeriod != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().histoTimeSpanEquals(histoPeriod));
			}
		}
		else{
			if(intradayWidth != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().intradayWidthEquals(intradayWidth));
			}
			if(intradayHeight != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().intradayHeightEquals(intradayHeight));
			}
		}
		
		spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().indexEquals(index));
		return chartIndexRepository.findAll(spec).stream().findFirst().orElse(null);
	}
}
