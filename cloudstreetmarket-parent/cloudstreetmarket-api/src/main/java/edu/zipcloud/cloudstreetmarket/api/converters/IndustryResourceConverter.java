package edu.zipcloud.cloudstreetmarket.api.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.resources.IndustryResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Industry;

@Component
public class IndustryResourceConverter implements Converter<Industry, IndustryResource> {
	
	@Autowired
	private EntityLinks entityLinks;
	
	@Override
	public IndustryResource convert(Industry industry) {
		IndustryResource resource = new IndustryResource(industry);
		resource.add(entityLinks.linkToSingleResource(industry));
		return resource;
	}
}