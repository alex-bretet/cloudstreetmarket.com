package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;

import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

@Repository
public interface StockProductRepository extends JpaRepository<StockProduct, String>, JpaSpecificationExecutor<StockProduct> {
	Page<StockProduct> findByNameStartingWith(String param, Pageable pageable);
	Page<StockProduct> findByNameStartingWith(String param, Specification<StockProduct> spec, Pageable pageable);
	
	Page<StockProduct> findByExchange(Exchange exchange, Pageable pageable);
	@EntityGraph(value = "StockProduct.detail", type = EntityGraphType.LOAD)
	Page<StockProduct> findByIndices(Index index, Pageable pageable);
}