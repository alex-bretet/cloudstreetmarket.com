package edu.zipcloud.cloudstreetmarket.api.services;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.yahoo.api.Yahoo2;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

import edu.zipcloud.cloudstreetmarket.core.converters.YahooQuoteToIndexConverter;
import edu.zipcloud.cloudstreetmarket.core.daos.ExchangeRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.HistoricalIndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexQuoteRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.IndexQuote;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;
import edu.zipcloud.core.util.DateUtil;

@Service
public class IndexServiceImpl implements IndexService {

	@Autowired
	private MarketRepository marketRepository;
	
	@Autowired
	private ExchangeRepository exchangeRepository;
	
	@Autowired
	private HistoricalIndexRepository historicalIndexRepository;
	
	@Autowired
	private IndexRepository indexRepository;
	
	@Autowired
	private SocialUserService usersConnectionRepository;
	
	@Autowired
	private YahooQuoteToIndexConverter yahooIndexConverter;
	
	@Autowired
	private IndexQuoteRepository indexQuoteRepository;
	
	@Override
	public Set<HistoricalIndex> gatherHisto(String id, Date fromDate, Date toDate, QuotesInterval interval){
		Set<HistoricalIndex> histoSet = getHisto(id, fromDate, toDate, interval);

		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateHistoFromYahoo(histoSet, id, fromDate, toDate, interval);
			return getHisto(id, fromDate, toDate, interval);
		}

		return histoSet;
	}

	private Set<HistoricalIndex> getHisto(String id, Date fromDate, Date toDate, QuotesInterval interval){
		Set<HistoricalIndex> histoSet = new LinkedHashSet<>();
		if(fromDate==null){
			histoSet.addAll(historicalIndexRepository.findOnLastIntraDay(id).stream().collect(Collectors.toSet()));
		}
		else{
			histoSet.addAll(historicalIndexRepository.findSelection(id, fromDate, toDate, interval));
		}
		return histoSet;
	}

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
	public Page<Index> gather(String exchangeId, MarketId marketId,
			Pageable pageable) {
		
		Page<Index> indices = getIndices(exchangeId, marketId, pageable);
		
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateIndexAndQuotesFromYahoo(indices.getContent().stream().collect(Collectors.toSet()));
			return getIndices(exchangeId, marketId, pageable);
		}
		
		return indices;
	}

	@Override
	public Index gather(String indexId) {
		Index index = indexRepository.findOne(indexId);
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateIndexAndQuotesFromYahoo(Sets.newHashSet(index));
			return indexRepository.findOne(indexId);
		}
		return index;
	}

	private void updateHistoFromYahoo(Set<HistoricalIndex> askedContent, String id, Date fromDate, Date toDate, QuotesInterval interval) {
		if(askedContent.isEmpty()){
			return;
		}
	}
	
	private void updateIndexAndQuotesFromYahoo(Set<Index> askedContent) {
		if(askedContent.isEmpty()){
			return;
		}
		
		Set<Index> recentlyUpdated = askedContent.stream()
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
						.map(Index::getId)
						.collect(Collectors.toList());
				
				Yahoo2 api = ((Yahoo2) connection.getApi());
				List<YahooQuote> yahooQuotes = api.financialOperations().getYahooQuotes(updatableTickers, token);

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

}
