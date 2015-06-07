package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;

public interface IndexRepositoryJpa extends JpaRepository<Index, String>{
	
	@Query("select t from Index t where t.exchange.market = ?1 and t.name is not null")
	Page<Index> findByMarket(Market market, Pageable pageable);
	
	Page<Index> findByExchange(Exchange exchange, Pageable pageable);
	List<Index> findAll();
	Page<Index> findAll(Pageable pageable);
}