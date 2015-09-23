package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.social.yahoo.module.YahooQuote;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@Table(name="currency_exchange_quote")
@XStreamAlias("currency_exchange_quote")
public class CurrencyExchangeQuote extends Quote {

	private static final long serialVersionUID = 7862727164220349639L;
	
	@Transient
	private String symbol;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public CurrencyExchangeQuote(){
	}

	public CurrencyExchangeQuote(YahooQuote q){
		setDate(new Date());
		setHigh(q.getHigh());
		setLow(q.getLow());
		setLast(q.getLast());
		setOpen(q.getOpen());
		setSymbol(q.getId());
		setPreviousClose(q.getPreviousClose());
	}

	//Avoid fetching lazy collections (session may be closed depending upon where toString is called from)
	@Override
	public String toString() {
		return "CurrencyExchangeQuote [symbol=" + symbol + ", id="
				+ id + ", getLastUpdate()=" + getLastUpdate() + ", getOpen()="
				+ getOpen() + ", getPreviousClose()=" + getPreviousClose()
				+ ", getLast()=" + getLast() + ", getDate()=" + getDate()
				+ ", getHigh()=" + getHigh() + ", getLow()=" + getLow() + "]";
	}
}
