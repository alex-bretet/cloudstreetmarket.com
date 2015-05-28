package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.zipcloud.cloudstreetmarket.core.entities.Market;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

public interface MarketRepository extends JpaRepository<Market, MarketId>{
	public <S extends Market> List<S> save(Iterable<S> entities);
}
