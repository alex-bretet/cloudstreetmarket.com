package edu.zipcloud.cloudstreetmarket.core.entities;

import javax.persistence.Entity;

@Entity
public class Industry extends AbstractId<Long> {
	
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "Industry [id="+id+", label=" + label + "]";
	}
}