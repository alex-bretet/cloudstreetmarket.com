package edu.zipcloud.cloudstreetmarket.core.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import edu.zipcloud.cloudstreetmarket.core.entities.Product;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketCode;

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
	  
	  public Specification<T> equals(String field, final MarketCode market) {
	    	return new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {    
	                return cb.equal(root.<MarketCode>get(field), market);
				}
	        };
	  }
}
