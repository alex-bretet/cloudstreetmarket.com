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
