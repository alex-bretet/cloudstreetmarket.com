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

	public IndustryResource() {
	}
	
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
	public String toString() {
		return "IndustryResource [industry=" + industry + "]";
	}
}