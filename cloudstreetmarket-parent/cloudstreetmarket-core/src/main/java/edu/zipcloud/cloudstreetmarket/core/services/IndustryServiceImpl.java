package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.daos.IndustryRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Industry;

@Service
public class IndustryServiceImpl implements IndustryService {

	@Autowired
	private IndustryRepository industryRepository;

	@Override
	public Page<Industry> getAll(Pageable pageable) {
		return industryRepository.findAll(pageable);
	}

	@Override
	public Industry get(Long id) {
		Industry industry = industryRepository.findOne(id);
		if(industry == null){
			throw new ResourceNotFoundException("No industry has been found for the provided industry ID: "+id);
		}
		return industry;
	}
}
