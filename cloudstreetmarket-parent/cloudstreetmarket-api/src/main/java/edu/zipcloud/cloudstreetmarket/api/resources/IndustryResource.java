package edu.zipcloud.cloudstreetmarket.api.resources;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.Industry;

@XStreamAlias("resource")
public class IndustryResource extends ResourceSupport implements Serializable{

	public static final String INDUSTRY = "industry";
	public static final String INDUSTRIES = "industries";
	public static final String INDUSTRIES_PATH = "/industries";

	private static final long serialVersionUID = 1L;
	
	private Industry industry;

	public IndustryResource(Industry industry) {
		super();
		this.industry = industry;
	}

	public Industry getIndustry() {
		return industry;
	}
	
	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((industry == null) ? 0 : industry.hashCode());
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
		IndustryResource other = (IndustryResource) obj;
		if (industry == null) {
			if (other.industry != null)
				return false;
		} else if (!industry.equals(other.industry))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IndustryResource [industry=" + industry.toString() + "]";
	}
}