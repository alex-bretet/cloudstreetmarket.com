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

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.social.yahoo.module.YahooQuote;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Table(name="index_quote")
@XStreamAlias("index_quote")
public class IndexQuote extends Quote {

	private static final long serialVersionUID = -5115694609904059298L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "index_code")
	@JsonIgnore
	@XStreamOmitField
	private Index index;

	@Transient
	private String symbol;
	
	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public IndexQuote(){
	}

	public IndexQuote(YahooQuote q){
		setDate(new Date());
		setHigh(q.getHigh());
		setLow(q.getLow());
		setLast(q.getLast());
		setOpen(q.getOpen());
		setSymbol(q.getId());
		setPreviousClose(q.getPreviousClose());
	}

	public IndexQuote(YahooQuote q, Index index) {
		this(q);
		setIndex(index);
	}

	//Avoid fetching lazy collections (session may be closed depending upon where toString is called from)
	@Override
	public String toString() {
		return "IndexQuote [index=" + index + ", symbol=" + symbol + ", id="
				+ id + ", getLastUpdate()=" + getLastUpdate() + ", getOpen()="
				+ getOpen() + ", getPreviousClose()=" + getPreviousClose()
				+ ", getLast()=" + getLast() + ", getDate()=" + getDate()
				+ ", getHigh()=" + getHigh() + ", getLow()=" + getLow() + "]";
	}
}
