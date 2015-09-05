package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;

@Api(value = "users activity feed", description = "Cloudstreet Market users activity feed") // Swagger annotation
@RestController
@RequestMapping(value="/users/feed", produces={"application/xml", "application/json"})
public class ActivityFeedController extends CloudstreetApiWCI{

	@Autowired
	private CommunityService communityService;
	
	@RequestMapping(method=GET)
	@ApiOperation(value = "Gets public user activities", notes = "Return a page of user-activities")
	public Page<UserActivityDTO> getPublicActivities(
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"id"}, direction=Direction.DESC) Pageable pageable){
		return communityService.getPublicActivity(pageable);
	}
}