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
package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.entities.Exchange;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;

@Transactional(readOnly = true)
@Repository
public class IndexRepositoryImpl implements IndexRepository{

	@PersistenceContext 
	private EntityManager em;
	
	@Autowired
	private IndexRepositoryJpa repo;

	@Override
	public List<Index> findAll() {
		return repo.findAll();
	}

	@Override
	public Page<Index> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public Index findOne(String id) {
		return repo.findOne(id);
	}

	@Override
	public Page<Index> findByExchange(Exchange exchange, Pageable pageable) {
		return repo.findByExchange(exchange, pageable);
	}

	@Override
	public Page<Index> findByMarket(Market market, Pageable pageable) {
        return repo.findByMarket(market, pageable);
	}

	@Override
	@Transactional
	public Index save(Index index) {
		return repo.save(index);
	}

	@Override
	@Transactional
	public List<Index> save(Iterable<Index> indices) {
		return repo.save(indices);
	}

	@Override
	public Page<Index> findByIdIn(List<String> tickers, Pageable pageable) {
		return repo.findByIdIn(tickers, pageable);
	}
}
