package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;

@Api(value = "monitoring", description = "Cloudstreet Market administration console") // Swagger annotation
@RestController
@RequestMapping(value="/monitoring", produces={"application/xml", "application/json"})
@PreAuthorize("hasRole('ADMIN')")
public class MonitoringController extends CloudstreetApiWCI{

	@Autowired
	private CommunityService communityService;
	
	@RequestMapping(value="/users/{username}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Details one account", notes = "")
	public User getUserDetails(@PathVariable String username){
		return communityService.findOne(username);
	}
	
	@RequestMapping(value="/users/{username}", method=DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete user-account", notes = "")
	public void deleteUser(@PathVariable String username){
		communityService.delete(username);
	}
	
	@RequestMapping(value="/users", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List user-accounts", notes = "")
	public Page<User> getUsers(@ApiIgnore @PageableDefault(size=10, page=0) Pageable pageable){
		return communityService.findAll(pageable);
	}
}