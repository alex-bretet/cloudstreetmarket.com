package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex.*;

@Entity
@DiscriminatorValue(DISCR)
public class HistoricalIndex extends Historic implements Serializable {

	private static final long serialVersionUID = -802306391915956578L;
	public static final String DISCR = "idx";
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "index_code")
	@JsonIgnore
	private Index index;

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}
}
