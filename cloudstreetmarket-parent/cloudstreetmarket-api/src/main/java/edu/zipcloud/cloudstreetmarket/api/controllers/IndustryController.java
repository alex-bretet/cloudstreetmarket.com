package edu.zipcloud.cloudstreetmarket.api.controllers;

import static edu.zipcloud.cloudstreetmarket.api.resources.IndustryResource.INDUSTRIES;
import static edu.zipcloud.cloudstreetmarket.api.resources.IndustryResource.INDUSTRIES_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import edu.zipcloud.cloudstreetmarket.api.converters.IndustryResourceConverter;
import edu.zipcloud.cloudstreetmarket.api.resources.IndustryResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Industry;
import edu.zipcloud.cloudstreetmarket.core.services.IndustryService;

@Api(value = INDUSTRIES, description = "Economic industries") // Swagger annotation
@RestController
@ExposesResourceFor(Industry.class)
@RequestMapping(value=INDUSTRIES_PATH, produces={"application/xml", "application/json"})
public class IndustryController extends CloudstreetApiWCI {
	
	@Autowired
	private IndustryService industryService;

	@Autowired
	private IndustryResourceConverter converter;
	
	@RequestMapping(method=GET)
	@ApiOperation(value = "Get industries", notes = "Return a page of industries")
	public Page<IndustryResource> getSeveral(
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"dailyLatestValue"}, direction=Direction.DESC) Pageable pageable){
		return industryService.getAll(pageable).map(converter);
	}
	
	@RequestMapping(value="/{industry}", method=GET)
	@ApiOperation(value = "Get an industry", notes = "Return an industry")
	public IndustryResource get(@PathVariable(value="industry") Long industryId){
		return converter.convert(industryService.get(industryId));
	}
}