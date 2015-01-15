package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;

public interface IndexProductRepository extends JpaRepository<Index, String>{
	
	List<Index> findByMarket(Market market);
	Index findByCode(String code);

}
