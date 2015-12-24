package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.services.ICommunityService;

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
	@ApiOperation(value = "Get public user activities", notes = "Return a page of user-activities")
	public Page<UserActivityDTO> getPublicActivities(
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"quote.date"}, direction=Direction.DESC) Pageable pageable){
		return communityService.getPublicActivity(pageable);
	}
}