package edu.zipcloud.cloudstreetmarket.ws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import edu.zipcloud.cloudstreetmarket.shared.util.Constants;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
	
    @Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages
		.simpMessageDestMatchers(Constants.WS_TOPIC_ACTIVITY_FEED_PATH, "/queue/*", "/app/queue/*").permitAll();
	}

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}
}
