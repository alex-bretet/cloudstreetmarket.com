package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("idx")
public class HistoricalIndex extends Historic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -802306391915956578L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "index_code")
	private IndexProduct index;
	
	public IndexProduct getIndex() {
		return index;
	}

	public void setIndex(IndexProduct index) {
		this.index = index;
	}
}
