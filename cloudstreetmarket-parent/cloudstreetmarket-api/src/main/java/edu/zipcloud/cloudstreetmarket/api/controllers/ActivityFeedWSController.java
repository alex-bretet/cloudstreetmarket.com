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

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static edu.zipcloud.cloudstreetmarket.api.config.WebSocketConfig.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;

@Api(value = "users activity feed", description = "Cloudstreet Market users activity feed") // Swagger annotation
@RestController
public class ActivityFeedWSController extends CloudstreetApiWCI{

	@Autowired
	private CommunityService communityService;

	@Autowired
	private SimpMessagingTemplate template;
	
    @MessageMapping(ACTIVITY_FEED_ENDPOINT)
    @SendTo(TOPIC_ACTIVITY_FEED_PATH)
    public UserActivityDTO handle(UserActivityDTO message) throws Exception {
        return message;
    }
    
	@RequestMapping(value="/users/feed/info", method=GET)
	public String infoWS(
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"date"}, direction=Direction.DESC) Pageable pageable){
		return "v0";
	}
}