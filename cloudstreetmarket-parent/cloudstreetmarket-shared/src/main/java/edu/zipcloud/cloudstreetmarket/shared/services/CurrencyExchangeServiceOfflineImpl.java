package edu.zipcloud.cloudstreetmarket.shared.services;

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
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.util.AuthenticationUtil;
import edu.zipcloud.core.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class CurrencyExchangeServiceOfflineImpl implements CurrencyExchangeServiceOffline {

	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;
	
	@Autowired
	private YahooQuoteToCurrencyExchangeConverter yahooCurrencyConverter;
	
	@Autowired
	private SocialUserService usersConnectionRepository;
	
    @Autowired
	public Environment env;

	@Override
	@Transactional
	public CurrencyExchange gather(String forUser, String ticker) {
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
		String token = usersConnectionRepository.getRegisteredSocialUser(guid).getAccessToken();
		ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(guid);
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
