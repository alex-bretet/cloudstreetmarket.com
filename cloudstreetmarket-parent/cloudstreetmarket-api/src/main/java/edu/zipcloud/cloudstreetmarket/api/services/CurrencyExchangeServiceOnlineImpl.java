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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.yahoo.api.Yahoo2;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import edu.zipcloud.cloudstreetmarket.core.converters.YahooQuoteToCurrencyExchangeConverter;
import edu.zipcloud.cloudstreetmarket.core.daos.CurrencyExchangeRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;
import edu.zipcloud.cloudstreetmarket.core.entities.SocialUser;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;
import edu.zipcloud.core.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class CurrencyExchangeServiceOnlineImpl implements CurrencyExchangeServiceOnline {

	@Autowired
	private SocialUserService usersConnectionRepository;
	
	@Autowired
	private ConnectionRepository connectionRepository;
	
	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;
	
	@Autowired
	private YahooQuoteToCurrencyExchangeConverter yahooCurrencyConverter;
	
    @Autowired
	public Environment env;

	@Override
	@Transactional
	public CurrencyExchange gather(String ticker) {
		Preconditions.checkNotNull(ticker, "The quote ticker is null before Yahoo call!");
		if(ticker.length() == 0){
			return null;
		}
		
		CurrencyExchange currencyExchange = currencyExchangeRepository.findOne(ticker);
		if(AuthenticationUtil.userHasRole(Role.ROLE_OAUTH2)){
			if(currencyExchange == null || !DateUtil.isRecent(currencyExchange.getLastUpdate(), 60)){
				updateCurrencyExchangeFromYahoo(ticker);
				return currencyExchangeRepository.findOne(ticker);
			}
		}
		return currencyExchange;
	}

	private void updateCurrencyExchangeFromYahoo(String ticker) {
		String guid = AuthenticationUtil.getPrincipal().getUsername();
		SocialUser socialUser = usersConnectionRepository.getRegisteredSocialUser(guid);
		if(socialUser == null){
			return;
		}
		
		String token = socialUser.getAccessToken();
		Connection<Yahoo2> connection = connectionRepository.getPrimaryConnection(Yahoo2.class);

        if (connection == null) {
        	return;
        }

		List<YahooQuote> yahooQuotes = connection.getApi().financialOperations().getYahooQuotes(Lists.newArrayList(ticker), token);
		if(yahooQuotes == null || yahooQuotes.size() > 1){
			throw new IllegalArgumentException("Currency ticker:"+ticker+" not found at Yahoo!");
		}
		currencyExchangeRepository.save(yahooCurrencyConverter.convert(yahooQuotes.get(0)));
	}
}
