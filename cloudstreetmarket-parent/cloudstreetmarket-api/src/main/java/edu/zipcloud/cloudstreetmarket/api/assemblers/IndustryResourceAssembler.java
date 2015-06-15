package edu.zipcloud.cloudstreetmarket.api.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.IndustryController;
import edu.zipcloud.cloudstreetmarket.api.resources.IndustryResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Industry;

@Component
public class IndustryResourceAssembler extends ResourceAssemblerSupport<Industry, IndustryResource> {
	
	@Autowired
	private EntityLinks entityLinks;
	
	public IndustryResourceAssembler() {
	    super(IndustryController.class, IndustryResource.class);
	}
	
	@Override
	public IndustryResource toResource(Industry industry) {
		IndustryResource industryResource = createResourceWithId(industry.getId(), industry);
		industryResource.setIndustry(industry);
		return industryResource;
	}
}