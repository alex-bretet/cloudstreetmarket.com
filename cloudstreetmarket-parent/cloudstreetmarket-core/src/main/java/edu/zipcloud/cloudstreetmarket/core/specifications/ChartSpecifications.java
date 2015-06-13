package edu.zipcloud.cloudstreetmarket.core.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;

import edu.zipcloud.cloudstreetmarket.core.entities.Chart;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartStock;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

public class ChartSpecifications<T extends Chart> {
	
	  public Specification<T> typeEquals(final ChartType type) {
	    	return new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.<String>get("type"), type);
				}
	        };
	  }
	  
	  public Specification<T> sizeEquals(final ChartHistoSize size) {
	    	return new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.<String>get("histoSize"), size);
				}
	        };
	  }
	  
	  public Specification<T> histoMovingAverageEquals(final ChartHistoMovingAverage histoMovingAverage) {
	    	return new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.<String>get("histoMovingAverage"), histoMovingAverage);
				}
	        };
	  }
	  
	  public Specification<T> histoTimeSpanEquals(final ChartHistoTimeSpan histoTimeSpan) {
	    	return new Specification<T>() {
				@Override
				public Predicate toPredicate(Root<T> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.<String>get("histoTimeSpan"), histoTimeSpan);
				}
	        };
	  }
	  
	  public Specification<T> intradayWidthEquals(Integer intradayWidth) {
    	return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.<String>get("intradayWidth"), intradayWidth);
			}
        };
	  }
	  
	  public Specification<T> intradayHeightEquals(Integer intradayHeight) {
    	return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.<String>get("intradayHeight"), intradayHeight);
			}
        };
	  }
	  
	  public Specification<ChartIndex> indexEquals(final Index index) {
	    	return new Specification<ChartIndex>() {
				@Override
				public Predicate toPredicate(Root<ChartIndex> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.<Index>get("index"), index);
				}
	        };
	  }
	  
	  public Specification<ChartStock> indexEquals(final StockProduct stock) {
	    	return new Specification<ChartStock>() {
				@Override
				public Predicate toPredicate(Root<ChartStock> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
	                return cb.equal(root.<StockProduct>get("stock"), stock);
				}
	        };
	  }
}
