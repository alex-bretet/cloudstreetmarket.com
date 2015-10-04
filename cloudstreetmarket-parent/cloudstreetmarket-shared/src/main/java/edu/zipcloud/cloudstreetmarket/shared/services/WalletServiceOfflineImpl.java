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
package edu.zipcloud.cloudstreetmarket.shared.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.StockProductRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.StockQuoteRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.TransactionRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.UserRepository;
import edu.zipcloud.cloudstreetmarket.core.dtos.WalletItemDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

@Service
@Transactional(readOnly = true)
public class WalletServiceOfflineImpl implements WalletServiceOffline {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StockQuoteRepository stockQuoteRepository;
	
	@Autowired
	private StockProductRepository stockProductRepository;

	@Autowired
	private StockProductServiceOffline stockProductService;
	
	@Autowired
	private CurrencyExchangeServiceOffline currencyExchangeService;
	
	@Override
	public List<WalletItemDTO> findBy(String forUser, String userName) {
	
		List<WalletItemDTO> results = new ArrayList<>();
		User user = userRepository.findOne(userName);
		
		List<String> productId = transactionRepository.findStockIdsByUser(user);
		
		stockProductService.gather(forUser, productId.toArray(new String[productId.size()]));

		Map<StockProduct, List<Transaction>> transPerStockProduct =
				transactionRepository.findByUser(user).stream()
				.sorted()
				.collect(Collectors.groupingBy(t -> t.getQuote().getStock()));

		Iterator<StockProduct> itr = transPerStockProduct.keySet().iterator();
		
		while (itr.hasNext()) {
			StockProduct stockProduct = itr.next();
			WalletItemDTO walletItem = new WalletItemDTO();
			walletItem.setCompanyName(stockProduct.getName());
			walletItem.setSymbol(stockProduct.getId());
			walletItem.setExchange(stockProduct.getExchange().getId());
			walletItem.setCurrency(stockProduct.getCurrency());
			
			List<Transaction> buysOnly = transPerStockProduct.get(stockProduct).stream()
				.filter(t -> UserActivityType.BUY.equals(t.getType()))
				.sorted()
				.collect(Collectors.toList());
			
			transPerStockProduct.get(stockProduct).stream()
				.filter(t -> UserActivityType.SELL.equals(t.getType()))
				.forEach(t -> {
					int parsedSoldQuantity = t.getQuantity();
					
					while (parsedSoldQuantity > 0){
						if(buysOnly.get(0).getQuantity() > parsedSoldQuantity){
							buysOnly.get(0).setQuantity(buysOnly.get(0).getQuantity() - parsedSoldQuantity);
							parsedSoldQuantity = 0;
						}
						else{
							parsedSoldQuantity = parsedSoldQuantity - buysOnly.get(0).getQuantity();
							buysOnly.remove(0);
						}
					}
				});

			CurrencyExchange currencyExchange = null;
			
			if(!user.getCurrency().equals(stockProduct.getQuote().getSupportedCurrency())){
				currencyExchange = currencyExchangeService.gather(forUser, stockProduct.getQuote().getSupportedCurrency().name() + user.getCurrency().name() + "=X");
			}
			
			double averageCostPerShare = buysOnly
				    .stream()
				    .mapToDouble(t -> t.getQuote().getAsk())
				    .average()
				    .getAsDouble();

			walletItem.setQuantity(buysOnly
				    .stream()
				    .mapToInt(Transaction::getQuantity)
				    .sum());
			
			if(!user.getCurrency().equals(stockProduct.getQuote().getSupportedCurrency())){
				walletItem.setLatestPrice(currencyExchange.getDailyLatestValue().doubleValue() * stockProduct.getQuote().getBid()); 
				walletItem.setLatestChangePercent(currencyExchange.getDailyLatestValue().doubleValue() * stockProduct.getDailyLatestChangePercent().doubleValue());
				walletItem.setAvgCostPerShare(currencyExchange.getDailyLatestValue().doubleValue() * averageCostPerShare);
				walletItem.setValuation(currencyExchange.getDailyLatestValue().doubleValue() * stockProduct.getQuote().getBid() * walletItem.getQuantity());
			}
			else{
				walletItem.setLatestPrice(stockProduct.getQuote().getBid()); 
				walletItem.setLatestChangePercent(stockProduct.getDailyLatestChangePercent().doubleValue());
				walletItem.setAvgCostPerShare(averageCostPerShare);
				walletItem.setValuation(stockProduct.getQuote().getBid() * walletItem.getQuantity());
			}
			
			walletItem.setCurrency(user.getCurrency().name());
			walletItem.setBookCost(walletItem.getAvgCostPerShare() * walletItem.getQuantity());
			walletItem.setProfit(walletItem.getValuation() - walletItem.getBookCost());
			walletItem.setProfitPercent(walletItem.getProfit()*100 / walletItem.getBookCost());

			results.add(walletItem);
		}

		return results;
	}
}
