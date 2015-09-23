package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.zipcloud.cloudstreetmarket.core.entities.Quote;

public interface QuoteRepository<T extends Quote> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
	
}

