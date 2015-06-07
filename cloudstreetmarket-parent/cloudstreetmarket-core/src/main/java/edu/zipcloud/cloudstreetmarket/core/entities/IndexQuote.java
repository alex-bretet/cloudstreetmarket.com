package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Table(name="index_quote")
@XStreamAlias("index_quote")
public class IndexQuote extends Quote implements Serializable{

	private static final long serialVersionUID = -8175317254623555447L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "index_code")
	@JsonIgnore
	@XStreamOmitField
	private Index index;

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}
}
