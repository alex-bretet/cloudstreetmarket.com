package edu.zipcloud.cloudstreetmarket.core.entities;

import static edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex.DISCR;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableSerializer;
import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableToIdConverter;

@Entity
@DiscriminatorValue(DISCR)
public class HistoricalIndex extends Historic implements Serializable {

	private static final long serialVersionUID = -802306391915956578L;
	public static final String DISCR = "idx";
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "index_code")
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("indexId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("indexId")
	private Index index;

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}
}
