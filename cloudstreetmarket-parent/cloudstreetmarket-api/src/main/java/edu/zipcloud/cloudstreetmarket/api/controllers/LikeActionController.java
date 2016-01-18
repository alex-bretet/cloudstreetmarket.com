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

import static edu.zipcloud.cloudstreetmarket.api.resources.LikeActionResource.ACTIONS_PATH;
import static edu.zipcloud.cloudstreetmarket.api.resources.LikeActionResource.LIKES;
import static edu.zipcloud.cloudstreetmarket.api.resources.LikeActionResource.LIKES_PATH;
import static edu.zipcloud.cloudstreetmarket.core.i18n.I18nKeys.I18N_TRANSACTIONS_USER_FORBIDDEN;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import static edu.zipcloud.cloudstreetmarket.shared.util.Constants.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import edu.zipcloud.cloudstreetmarket.api.assemblers.LikeActionResourceAssembler;
import edu.zipcloud.cloudstreetmarket.api.resources.LikeActionResource;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.LikeAction;
import edu.zipcloud.cloudstreetmarket.core.services.LikeActionService;
import edu.zipcloud.cloudstreetmarket.shared.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Api(value = LIKES, description = "Like actions") // Swagger annotation
@RestController
@ExposesResourceFor(LikeAction.class)
@RequestMapping(value=ACTIONS_PATH + LIKES_PATH, produces={"application/xml", "application/json"})
public class LikeActionController extends CloudstreetApiWCI<LikeAction> {
	
	private static final Logger logger = LogManager.getLogger(LikeActionController.class);
	
	@Autowired
	private LikeActionService likeActionService;
	
	@Autowired
	private LikeActionResourceAssembler assembler;

	@RequestMapping(method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Search for likes", notes = "")
	public PagedResources<LikeActionResource> search(
			@ApiParam(value="Action Id: 123") @RequestParam(value="action", required=true) Long actionId,
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"id"}, direction=Direction.DESC) Pageable pageable){
		return pagedAssembler.toResource(likeActionService.findBy(pageable, actionId), assembler);
	}
	
	@RequestMapping(value="/{id}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a like action", notes = "Return one like")
	public LikeActionResource get(@ApiParam(value="Like-action id: 123") @PathVariable(value="id") Long actionId){
		return assembler.toResource(likeActionService.get(actionId));
	}
	
	@RequestMapping(method=POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a Like")
	public LikeActionResource post(@RequestBody LikeAction likeAction, HttpServletResponse response) {
		likeActionService.hydrate(likeAction);
		
		if(!likeAction.getUser().getUsername().equals(getPrincipal().getUsername())){
			throw new AccessDeniedException(bundle.get(I18N_TRANSACTIONS_USER_FORBIDDEN));
		}
		
		likeAction = likeActionService.create(likeAction);
    	logger.info("User {} likes action id {}", getPrincipal().getUsername(), likeAction.getTargetAction().getId());
		messagingTemplate.convertAndSend(Constants.AMQP_USER_ACTIVITY_QUEUE, new UserActivityDTO(likeAction));

		LikeActionResource resource = assembler.toResource(likeAction);
		response.setHeader(LOCATION_HEADER, resource.getLink("self").getHref());
		return resource;
	}
	
	@RequestMapping(value="/{id}", method=DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deletes a like", notes = "Deletes one like")
	public void delete(@ApiParam(value="Like-action id: 123") @PathVariable(value="id") Long actionId){
		likeActionService.delete(actionId);
	}
}