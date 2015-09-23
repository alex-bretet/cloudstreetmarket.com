package edu.zipcloud.cloudstreetmarket.core.entities;

import javax.persistence.Entity;

@Entity
public class Industry extends AbstractId<Long> {

	private static final long serialVersionUID = 2270717282944054394L;
	
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