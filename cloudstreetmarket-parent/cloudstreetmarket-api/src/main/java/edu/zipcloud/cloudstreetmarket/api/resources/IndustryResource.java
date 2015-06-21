package edu.zipcloud.cloudstreetmarket.api.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.Industry;

@XStreamAlias("resource")
public class IndustryResource extends Resource<Industry> {

	public static final String INDUSTRY = "industry";
	public static final String INDUSTRIES = "industries";
	public static final String INDUSTRIES_PATH = "/industries";
	
	public IndustryResource(Industry content, Link... links) {
		super(content, links);
	}
}