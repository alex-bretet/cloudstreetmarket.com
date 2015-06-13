package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.zipcloud.cloudstreetmarket.core.entities.ChartIndex;

public interface ChartIndexRepository extends JpaRepository<ChartIndex, Long>, JpaSpecificationExecutor<ChartIndex> {

}
