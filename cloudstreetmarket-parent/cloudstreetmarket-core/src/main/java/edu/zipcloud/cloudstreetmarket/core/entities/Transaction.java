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
package edu.zipcloud.cloudstreetmarket.core.entities;

import static edu.zipcloud.cloudstreetmarket.core.entities.Transaction.*;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableSerializer;
import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableToIdConverter;

@Validated
@Entity
@DiscriminatorValue(DISCR)
@XStreamAlias("transaction")
@NamedQueries({
    @NamedQuery(name=FIND_STOCK_ID_BY_USER,
            query="select t.quote.stock.id from Transaction t where t.user = :user GROUP BY t.quote.stock.id"),
    @NamedQuery(name=FIND_BY_USER_AND_STOCK,
                query="select t from Transaction t where t.user = :user and t.quote.stock.id = :productId"),
    @NamedQuery(name=COUNT_BY_USER_AND_STOCK,
                query="select count(t) from Transaction t where t.user = :user and t.quote.stock.id = :productId"),
    @NamedQuery(name=FIND_FROM_QUOTE_DATE,
                query="select t from Transaction t where t.quote.date >= :quoteDate"),
    @NamedQuery(name=GET_ALL_ORDER_BY,
                query="select t from Transaction t ORDER BY t.quote.date desc")
}) 
public class Transaction extends Action implements Comparable<Transaction>, Serializable {

	private static final long serialVersionUID = -5395464106046036788L;
	
	public static final String GET_ALL_ORDER_BY = "Transaction.getAllAndOrderBy";
	public static final String FIND_STOCK_ID_BY_USER = "Transaction.findStockIdByUser";
	public static final String FIND_FROM_QUOTE_DATE = "Transaction.findFromQuoteDate";
	public static final String FIND_BY_USER_AND_STOCK = "Transaction.findByUserAndStock";
	public static final String COUNT_BY_USER_AND_STOCK = "Transaction.countByUserAndStock";
	
	public static final String DISCR = "trans";
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stock_quote_id")
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("quoteId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("quoteId")
	private StockQuote quote;

	private int quantity;

	@Column(name="last_update", insertable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", precision = 10, scale = 5)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public StockQuote getQuote() {
		return quote;
	}

	public void setQuote(StockQuote quote) {
		this.quote = quote;
	}

	//Avoid fetching lazy collections here (session may be closed depending upon where toString is called from)
	@Override
	public String toString() {
		return "Transaction [quote=" + quote + ", quantity=" + quantity
				+ ", lastUpdate=" + lastUpdate + ", id=" + id + ", getType()="
				+ getType() + ", getDate()=" + getDate() + "]";
	}

	@Override
	public int compareTo(Transaction arg0) {
		return this.getDate().compareTo(arg0.getDate());
	}
}
