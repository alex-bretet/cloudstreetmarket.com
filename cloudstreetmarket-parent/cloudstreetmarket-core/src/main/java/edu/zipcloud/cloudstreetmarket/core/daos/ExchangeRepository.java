package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;

public interface ExchangeRepository extends JpaRepository<Exchange, String>{
	Page<Exchange> findByMarket(Pageable pageable);
}
