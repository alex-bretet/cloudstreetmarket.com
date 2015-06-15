package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;

public interface IndexRepository{
	Page<Index> findByMarket(Market findOne, Pageable pageable);
	Page<Index> findByExchange(Exchange exchange, Pageable pageable);
	List<Index> findAll();
	Page<Index> findAll(Pageable pageable);
	Index findOne(String id);
	Index save(Index index);
	List<Index> save(Iterable<Index> indices);
}
