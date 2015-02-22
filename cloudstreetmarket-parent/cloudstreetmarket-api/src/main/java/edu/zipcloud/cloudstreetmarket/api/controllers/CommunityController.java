package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.Api;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.params.SortDirectionParam;
import edu.zipcloud.cloudstreetmarket.core.params.SortFieldParam;
import edu.zipcloud.cloudstreetmarket.core.services.ICommunityService;
import edu.zipcloud.util.SortUtil;

@Api(value = "users", description = "Cloudstreet Market users") // Swagger annotation
@RestController
@RequestMapping(value="/users", produces={"application/xml", "application/json"})
public class CommunityController extends CloudstreetApiWCI{

	@Autowired
	private WebApplicationContext webAppContext;

	@Autowired
	@Qualifier("communityServiceImpl")
	private ICommunityService communityService;
	
	@RequestMapping(value="/activity", method=GET)
	@ResponseStatus(HttpStatus.OK)
	public Page<UserActivityDTO> getPublicActivities(
			@RequestParam(value="sf", defaultValue="quote.date") SortFieldParam sortFields, 
			@RequestParam(value="sd", defaultValue="desc") SortDirectionParam sortDirections, 
			@RequestParam(value="pn", defaultValue="0") int pageNumber, 
			@RequestParam(value="ps", defaultValue="10") int pageSize){
		return communityService.getPublicActivity(new PageRequest(
				pageNumber, 
				pageSize, 
				SortUtil.buildSort(sortFields.getFields(), sortDirections.getDirections())));
	}
}