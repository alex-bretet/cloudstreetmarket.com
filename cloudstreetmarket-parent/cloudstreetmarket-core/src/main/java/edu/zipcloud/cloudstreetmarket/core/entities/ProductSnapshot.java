package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ProductSnapshot {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	protected Integer id;
	
	private Date date;
	
	private double open;
	
	@Column(name = "previous_close")
	private double previousClose;
	
	private double last;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}

	public double getLast() {
		return last;
	}

	public void setLast(double last) {
		this.last = last;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
