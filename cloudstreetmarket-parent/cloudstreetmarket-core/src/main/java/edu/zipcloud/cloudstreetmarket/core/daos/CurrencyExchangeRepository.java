package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, String>{
}
