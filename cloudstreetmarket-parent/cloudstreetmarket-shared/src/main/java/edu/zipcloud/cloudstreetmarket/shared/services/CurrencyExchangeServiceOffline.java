package edu.zipcloud.cloudstreetmarket.shared.services;

import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;

public interface CurrencyExchangeServiceOffline{
	CurrencyExchange gather(String forUser, String ticker);
}
