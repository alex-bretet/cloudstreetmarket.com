package edu.zipcloud.cloudstreetmarket.core.entities;

import static edu.zipcloud.cloudstreetmarket.core.entities.ChartIndex.DISCR;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableSerializer;
import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableToIdConverter;

@Entity
@DiscriminatorValue(DISCR)
public class ChartIndex extends Chart implements Serializable {

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
	
	public ChartIndex(){
	}

	public ChartIndex(Index index, ChartType type, ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage, 
						ChartHistoTimeSpan histoPeriod, Integer intradayWidth, Integer intradayHeight, String path) {
		super(type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight, path);
		this.index= index;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quote other = (Quote) obj;
		if (getId() != other.getId())
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}
}
