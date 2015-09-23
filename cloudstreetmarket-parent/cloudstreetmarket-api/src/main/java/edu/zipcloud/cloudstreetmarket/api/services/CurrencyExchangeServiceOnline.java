package edu.zipcloud.cloudstreetmarket.api.services;

import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;

public interface CurrencyExchangeServiceOnline{
	CurrencyExchange gather(String ticker);
}
