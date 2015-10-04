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
package edu.zipcloud.cloudstreetmarket.core.specifications;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import edu.zipcloud.cloudstreetmarket.core.entities.Product;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

public class ProductSpecifications<T extends Product> {

	  public Specification<T> nameStartsWith(final String searchTerm) {
	    	return new Specification<T>() {
	            private String startWithPattern(final String searchTerm) {
	                StringBuilder pattern = new StringBuilder();
	                pattern.append(searchTerm.toLowerCase());
	                pattern.append("%");
	                return pattern.toString();
	            }

				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {    
	                return cb.like(cb.lower(root.<String>get("name")), startWithPattern(searchTerm));
				}
	        };
	  }
	  
	  public Specification<T> marketIdEquals(final MarketId marketId) {
	    	return new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.<String>get("exchange").get("market").get("id"), marketId);
				}
	        };
	  }
	  
	  public Specification<T> exchangeIdEquals(final String exchangeId) {
	    	return new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.<String>get("exchange").get("id"), exchangeId);
				}
	        };
	  }
	  
	  public Specification<T> exchangeNotNull() {
	    	return new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.isNotNull(root.<String>get("exchange"));
				}
	        };
	  }
	  
	  public Specification<T> nameNotNull() {
	    	return new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.isNotNull(root.<String>get("name"));
				}
	        };
	  }
	  
	  public Specification<T> currencyNotNull() {
	    	return new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.isNotNull(root.<String>get("currency"));
				}
	        };
	  }

	  
	  public Specification<T> hasAPrice() {
	    	return new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.greaterThan(root.<BigDecimal>get("dailyLatestValue"), BigDecimal.valueOf(0));
				}
	        };
	  }
	  

}
