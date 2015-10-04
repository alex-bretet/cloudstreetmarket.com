/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import edu.zipcloud.cloudstreetmarket.core.entities.User;

@Api(value = "sessions", description = "Sessions manager")
@RestController
@RequestMapping(value="sessions", produces={"application/json"})
public class SessionController extends CloudstreetApiWCI{

	@RequestMapping(value="/{username}", method=GET)
	@ApiOperation(value = "Get the session Id of the current authenticated user")
	@PreAuthorize("isAuthenticated()")
	public String get(@ApiParam(value="Username: johnd") @PathVariable(value="username") String username, HttpServletRequest request, Principal principal){
		if(principal == null || !principal.getName().equals(username)){
			throw new AccessDeniedException("You must be authenticated as "+username +"!");
		}
		HttpSession session = request.getSession(false);
		if(session != null){
			return session.getId();
		}
    	return "";
	}
	
	@RequestMapping(value="/current", method=GET)
	@ApiOperation(value = "Get the session Id of the current user")
	public String getCurrent(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null){
			return session.getId();
		}
    	return "";
	}
	
	@RequestMapping(value="/login", method=POST)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Identifies the provided user")
	public void login(@RequestBody User user, 
							@RequestHeader(value="Spi", required=false) String guid, 
								@RequestHeader(value="OAuthProvider", required=false) String provider, 
									HttpServletResponse response,
										HttpServletRequest request){
		user = communityService.identifyUser(user);
		if(isNotBlank(guid)){
			usersConnectionRepository.bindSocialUserToUser(guid, user, provider);
		}
    	communityService.signInUser(user);
	}
	
	@RequestMapping(value="/current", method=DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Invalidate the user Session")
	public void logout(HttpServletRequest request){
		request.getSession().invalidate();
	}
}
