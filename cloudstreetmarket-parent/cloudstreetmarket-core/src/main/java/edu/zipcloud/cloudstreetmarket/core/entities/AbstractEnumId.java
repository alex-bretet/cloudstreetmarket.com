package edu.zipcloud.cloudstreetmarket.core.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static javax.persistence.EnumType.*;

import org.springframework.hateoas.Identifiable;

@MappedSuperclass
public class AbstractEnumId<ID extends Serializable> implements Identifiable<ID> {

	@Id 
	@Enumerated(STRING)
	protected ID id;

	@Override
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id.toString();
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
		
		AbstractEnumId<?> other = (AbstractEnumId<?>) obj;
		return Objects.equals(this.id, other.id);
	}
}