package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="market")
public class Market implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6433721069248439324L;

	@Id
	private String id;
	
	private String name;
	
	@OneToMany(mappedBy = "market", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<Index> indices = new LinkedHashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
}
