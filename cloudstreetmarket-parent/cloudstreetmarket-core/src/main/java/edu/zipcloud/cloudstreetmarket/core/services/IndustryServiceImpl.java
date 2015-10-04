/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.IndustryRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Industry;

@Service
@Transactional(readOnly = true)
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
