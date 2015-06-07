package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;

@Repository
public class IndexRepositoryImpl implements IndexRepository{

	@PersistenceContext 
	private EntityManager em;
	
	@Autowired
	private IndexRepositoryJpa repo;

	@Override
	public List<Index> findAll() {
		return repo.findAll();
	}

	@Override
	public Page<Index> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public Index findOne(String id) {
		return repo.findOne(id);
	}

	@Override
	public Page<Index> findByExchange(Exchange exchange, Pageable pageable) {
		return repo.findByExchange(exchange, pageable);
	}

	@Override
	public Page<Index> findByMarket(Market market, Pageable pageable) {
        return repo.findByMarket(market, pageable);
	}

	@Override
	public Index save(Index index) {
		return repo.save(index);
	}

	@Override
	public List<Index> save(Iterable<Index> indices) {
		return repo.save(indices);
	}
}
