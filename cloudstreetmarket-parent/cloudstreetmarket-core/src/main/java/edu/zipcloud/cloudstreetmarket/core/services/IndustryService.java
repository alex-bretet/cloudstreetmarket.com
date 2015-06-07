package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.zipcloud.cloudstreetmarket.core.entities.Industry;

public interface IndustryService{
	Page<Industry> getAll(Pageable pageable);
	Industry get(String id);
}
