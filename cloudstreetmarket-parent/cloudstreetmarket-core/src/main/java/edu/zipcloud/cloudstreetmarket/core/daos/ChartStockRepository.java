package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.zipcloud.cloudstreetmarket.core.entities.ChartStock;

public interface ChartStockRepository extends JpaRepository<ChartStock, Long>, JpaSpecificationExecutor<ChartStock> {

}
