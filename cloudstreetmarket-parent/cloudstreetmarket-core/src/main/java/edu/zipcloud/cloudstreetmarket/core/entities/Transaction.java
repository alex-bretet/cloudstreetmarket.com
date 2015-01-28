package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import edu.zipcloud.cloudstreetmarket.core.enums.Action;

@Entity
@Table(name="transaction")
public class Transaction implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6206553357043559298L;
	
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_name")
	private User user;
	
	@Enumerated(EnumType.STRING)
	private Action type;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stock_quote_id")
	private StockQuote quote;

	private int quantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Action getType() {
		return type;
	}

	public void setType(Action type) {
		this.type = type;
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
}
