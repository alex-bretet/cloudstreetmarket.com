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
package edu.zipcloud.cloudstreetmarket.ws.controllers;

import static edu.zipcloud.cloudstreetmarket.shared.util.Constants.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;

@RestController
public class ActivityFeedWSController extends CloudstreetWebSocketWCI{

    @MessageMapping(ACTIVITY_FEED_ENDPOINT)
    @SendTo(WS_TOPIC_ACTIVITY_FEED_PATH)
    public UserActivityDTO handle(UserActivityDTO message) throws Exception {
        return message;
    }
    
    @RequestMapping(value=ACTIVITY_FEED_ENDPOINT+"/info", produces={"application/json"})
	@ResponseBody
	public String info(HttpServletRequest request) {
	    return "v0";
	}
}