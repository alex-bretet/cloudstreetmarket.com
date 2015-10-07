/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.api.services;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

import edu.zipcloud.cloudstreetmarket.core.entities.ChartStock;
import edu.zipcloud.cloudstreetmarket.core.entities.SocialUser;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.entities.StockQuote;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;
import edu.zipcloud.cloudstreetmarket.shared.services.StockProductServiceImpl;
import edu.zipcloud.core.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class StockProductServiceOnlineImpl extends StockProductServiceImpl implements StockProductServiceOnline {

	@Autowired
	private ConnectionRepository connectionRepository;
	
	@Override
	@Transactional
	public Page<StockProduct> gather(String indexId, String exchangeId,
			MarketId marketId, String startWith,
			Specification<StockProduct> spec, Pageable pageable) {

		Page<StockProduct> stocks = get(indexId, exchangeId, marketId, startWith, spec, pageable, false);
		
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateStocksAndQuotesFromYahoo(stocks.getContent().stream().collect(Collectors.toSet()));
			return get(indexId, exchangeId, marketId, startWith, spec, pageable, false);
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
			SocialUser socialUser = usersConnectionRepository.getRegisteredSocialUser(guid);
			if(socialUser == null){
				return;
			}
			String token = socialUser.getAccessToken();
			Connection<Yahoo2> connection = connectionRepository.getPrimaryConnection(Yahoo2.class);
			
	        if (connection != null) {
				askedContent.removeAll(recentlyUpdated);

				Map<String, StockProduct> updatableTickers = askedContent.stream()
						.collect(Collectors.toMap(StockProduct::getId, Function.identity()));
				
				List<YahooQuote> yahooQuotes = connection.getApi().financialOperations().getYahooQuotes(new ArrayList<String>(updatableTickers.keySet()), token);
				
				Set<StockProduct> updatableProducts = yahooQuotes.stream()
					.filter(yq -> StringUtils.isNotBlank(yq.getExchange()))
					.filter(yq -> updatableTickers.get(yq.getId()) != null)
					.map(yq -> {
						StockQuote sq = new StockQuote(yq, updatableTickers.get((yq.getId())));
						return syncProduct(updatableTickers.get((yq.getId())), sq);
					})
					.collect(Collectors.toSet());
				
				if(!updatableProducts.isEmpty()){
					stockProductRepository.save(updatableProducts);
				}
				
				//This job below should decrease with the time
				Set<StockProduct> removableProducts = yahooQuotes.stream()
						.filter(yq -> StringUtils.isBlank(yq.getExchange()))
						.map(yq -> {
							StockQuote sq = new StockQuote(yq, updatableTickers.get((yq.getId())));
							return syncProduct(updatableTickers.get((yq.getId())), sq);
						})
						.collect(Collectors.toSet());
					
				if(!removableProducts.isEmpty()){
					stockProductRepository.delete(removableProducts);
				}
	        }
		}
	}

	@Override
	@Transactional
	public StockProduct gather(String stockProductId) {
		StockProduct stock = stockProductRepository.findOne(stockProductId);
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			updateStocksAndQuotesFromYahoo(stock != null ? Sets.newHashSet(stock) : Sets.newHashSet(new StockProduct(stockProductId)));
			return stockProductRepository.findOne(stockProductId);
		}
		return stock;
	}

	@Override
	@Transactional
	public ChartStock gather(String stockProductId, ChartType type,
			ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth,
			Integer intradayHeight) throws ResourceNotFoundException {
		Preconditions.checkNotNull(type, "ChartType must not be null!");
		
		StockProduct stock = gather(stockProductId);

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
		SocialUser socialUser = usersConnectionRepository.getRegisteredSocialUser(guid);
		if(socialUser == null){
			return;
		}
		String token = socialUser.getAccessToken();
		Connection<Yahoo2> connection = connectionRepository.getPrimaryConnection(Yahoo2.class);
		
        if (connection != null) {
			byte[] yahooChart = connection.getApi().financialOperations().getYahooChart(stock.getId(), type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight, token);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm");
			LocalDateTime dateTime = LocalDateTime.now();
			String formattedDateTime = dateTime.format(formatter); // "1986-04-08_1230"
			String imageName = stock.getId().toLowerCase()+"_"+type.name().toLowerCase()+"_"+formattedDateTime+".png";
	    	String pathToYahooPicture = env.getProperty("user.home").concat(env.getProperty("pictures.yahoo.path")).concat(File.separator+imageName);
	    	
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
	
	private StockProduct syncProduct(StockProduct stockProduct, StockQuote quote){
		stockProduct.setHigh(BigDecimal.valueOf(quote.getHigh()));
		stockProduct.setLow(BigDecimal.valueOf(quote.getLow()));
		stockProduct.setDailyLatestChange(BigDecimal.valueOf(quote.getLastChange()));
		stockProduct.setDailyLatestChangePercent(BigDecimal.valueOf(quote.getLastChangePercent()));
		stockProduct.setDailyLatestValue(BigDecimal.valueOf(quote.getLast()));
		stockProduct.setPreviousClose(BigDecimal.valueOf(quote.getPreviousClose()));
		stockProduct.setOpen(BigDecimal.valueOf(quote.getOpen()));
		if(!StringUtils.isEmpty(quote.getExchange())){
			stockProduct.setExchange(exchangeService.get(quote.getExchange()));
		}
		if(!StringUtils.isEmpty(quote.getCurrency())){
			stockProduct.setCurrency(quote.getCurrency());
		}
		stockProduct.setQuote(quote);
		return stockProduct;
	}

	@Override
	@Transactional
	public List<StockProduct> gather(String[] stockProductId) {
		List<StockProduct> stockProducts = stockProductRepository.findByIdIn(Arrays.asList(stockProductId));
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			Set<StockProduct> fallBack = Arrays.asList(stockProductId).stream().map(id -> new StockProduct(id)).collect(Collectors.toSet());
			updateStocksAndQuotesFromYahoo(!stockProducts.isEmpty()? Sets.newHashSet(stockProducts) : fallBack);
			return stockProductRepository.findByIdIn(Arrays.asList(stockProductId));
		}
		return stockProducts;
	}
	
	@Override
	@Transactional
	public Map<String, StockProduct> gatherAsMap(String[] stockProductId) {
		List<StockProduct> stockProducts = gather(stockProductId);
		return stockProducts.stream().collect(
	            Collectors.toMap(StockProduct::getId, i -> i));
	}
}
