package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.hateoas.Identifiable;

@MappedSuperclass
public class AbstractTableGeneratedId<ID extends Serializable> implements Identifiable<ID> {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	protected ID id;
	
	@Override
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		AbstractTableGeneratedId<?> other = (AbstractTableGeneratedId<?>) obj;
		return Objects.equals(this.id, other.id);
	}
}