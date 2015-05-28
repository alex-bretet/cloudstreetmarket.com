package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;

import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

@Repository
public interface StockProductRepository extends ProductRepository<StockProduct> {
	
	@EntityGraph(value = "Index.detail", type = EntityGraphType.LOAD)
	Page<StockProduct> findByIndices(Index index, Pageable pageable);

}
