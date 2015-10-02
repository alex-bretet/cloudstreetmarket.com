package edu.zipcloud.cloudstreetmarket.api.services;

import java.io.File;
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import edu.zipcloud.cloudstreetmarket.api.controllers.ChartStockController;
import edu.zipcloud.cloudstreetmarket.core.converters.YahooQuoteToIndexConverter;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexQuoteRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.IndexQuote;
import edu.zipcloud.cloudstreetmarket.core.entities.SocialUser;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;
import edu.zipcloud.cloudstreetmarket.shared.services.IndexServiceImpl;
import edu.zipcloud.core.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class IndexServiceOnlineImpl extends IndexServiceImpl implements IndexServiceOnline {
	
	private static final Logger log = Logger.getLogger(IndexServiceOnlineImpl.class);
	
	@Autowired
	private ConnectionRepository connectionRepository;

	@Autowired
	private YahooQuoteToIndexConverter yahooIndexConverter;
	
	@Autowired
	private IndexQuoteRepository indexQuoteRepository;
	
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
			SocialUser socialUser = usersConnectionRepository.getRegisteredSocialUser(guid);
			if(socialUser == null){
				return;
			}
			
			String token = socialUser.getAccessToken();
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
			log.debug("chartIndex!=null && !chartIndex.isExpired(ttl)");
			return chartIndex;
		}
		else if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			log.debug("AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)");
			log.debug("updateChartIndexFromYahoo("+index+", "+type+", "+histoSize+", "+histoAverage+", "+histoPeriod+", "+intradayWidth+", "+intradayHeight+")");
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
		SocialUser socialUser = usersConnectionRepository.getRegisteredSocialUser(guid);
		if(socialUser == null){
			return;
		}
		
		String token = socialUser.getAccessToken();
		
        Connection<Yahoo2> connection = connectionRepository.getPrimaryConnection(Yahoo2.class);

        if (connection != null) {
			byte[] yahooChart = connection.getApi().financialOperations().getYahooChart(index.getId(), type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight, token);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm");
			LocalDateTime dateTime = LocalDateTime.now();
			String formattedDateTime = dateTime.format(formatter); // "1986-04-08_1230"
			String imageName = index.getId().toLowerCase()+"_"+type.name().toLowerCase()+"_"+formattedDateTime+".png";
	    	String pathToYahooPicture = env.getProperty("user.home").concat(env.getProperty("pictures.yahoo.path")).concat(File.separator+imageName);
	    	
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
}
