package edu.zipcloud.cloudstreetmarket.api.resources;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;

@XStreamAlias("resource")
public class HistoricalIndexResource extends ResourceSupport implements Serializable{

	public static final String HISTO = "histo";
	public static final String HISTO_PATH = "/histo";
	
	private static final long serialVersionUID = 1L;

	@XStreamAlias("histoIndex")
	private HistoricalIndex historicalIndex;

	public HistoricalIndexResource(HistoricalIndex historicalIndex) {
		super();
		this.historicalIndex = historicalIndex;
	}

	public HistoricalIndex getHistoricalIndex() {
		return historicalIndex;
	}

	public void setHistoricalIndex(HistoricalIndex historicalIndex) {
		this.historicalIndex = historicalIndex;
	}

	public static HistoricalIndexResource build(HistoricalIndex historicalIndex){
		return new HistoricalIndexResource(historicalIndex);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((historicalIndex == null) ? 0 : historicalIndex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricalIndexResource other = (HistoricalIndexResource) obj;
		if (historicalIndex == null) {
			if (other.historicalIndex != null)
				return false;
		} else if (!historicalIndex.equals(other.historicalIndex))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HistoricalIndexResource [historicalIndex=" + historicalIndex.toString() + "]";
	}
}