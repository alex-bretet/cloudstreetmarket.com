package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="index_snapshot")
public class IndexSnapshot extends ProductSnapshot implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8175317254623555447L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "index_code")
	private IndexProduct index;

	public IndexProduct getIndex() {
		return index;
	}

	public void setIndex(IndexProduct index) {
		this.index = index;
	}

}
