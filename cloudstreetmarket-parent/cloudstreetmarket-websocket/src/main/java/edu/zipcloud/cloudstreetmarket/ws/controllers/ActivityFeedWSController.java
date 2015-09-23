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