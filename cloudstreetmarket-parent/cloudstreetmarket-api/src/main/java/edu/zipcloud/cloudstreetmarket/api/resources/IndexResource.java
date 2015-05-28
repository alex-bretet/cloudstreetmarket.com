package edu.zipcloud.cloudstreetmarket.api.resources;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.Index;

@XStreamAlias("index")
public class IndexResource extends ResourceSupport implements Serializable{

	public static final String INDICES = "indices";
	public static final String INDICES_PATH = "/indices";
	
	private static final long serialVersionUID = 1L;
	private Index index;

	public IndexResource(Index index) {
		super();
		this.index = index;
	}

	public Index getIndex() {
		return index;
	}
	
	public void setIndex(Index index) {
		this.index = index;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((index == null) ? 0 : index.hashCode());
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
		IndexResource other = (IndexResource) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IndexResource [index=" + index.toString() + "]";
	}
}