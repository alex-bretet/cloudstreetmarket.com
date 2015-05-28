package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;

@Entity
public class Market extends AbstractEnumId<MarketId> implements Serializable  {

	private static final long serialVersionUID = -6433721069248439324L;
	
	private String name;

	@OneToMany(mappedBy = "market", cascade = { CascadeType.ALL }, fetch=FetchType.LAZY)
	@JsonIgnore
	private Set<Index> indices = new LinkedHashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Index> getIndices() {
		return indices;
	}

	public void setIndices(Set<Index> indices) {
		this.indices = indices;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Market other = (Market) obj;
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

	//Avoid fetching lazy collections at this stage (session may be closed)
	@Override
	public String toString() {
		return "Market [id="+getId().toString()+", name=" + name + "]";
	}

}
