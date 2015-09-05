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