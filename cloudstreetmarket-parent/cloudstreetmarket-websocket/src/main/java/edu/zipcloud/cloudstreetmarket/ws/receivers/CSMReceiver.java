package edu.zipcloud.cloudstreetmarket.ws.receivers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.shared.util.Constants;

@Component
public class CSMReceiver {

	@Autowired
	protected SimpMessagingTemplate simpMessagingTemplate;
	
	@RabbitListener(queues = Constants.JMS_USER_ACTIVITY_QUEUE)
	public void handleMessage(UserActivityDTO message) {
	    simpMessagingTemplate.convertAndSend(Constants.WS_TOPIC_ACTIVITY_FEED_PATH, message);
	}
}