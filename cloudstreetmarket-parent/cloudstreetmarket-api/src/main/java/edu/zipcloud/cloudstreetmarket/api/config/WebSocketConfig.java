package edu.zipcloud.cloudstreetmarket.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@ComponentScan("edu.zipcloud.cloudstreetmarket.api")
@EnableWebSocketMessageBroker
@EnableScheduling
@EnableAsync
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	public static final String ACTIVITY_FEED_ENDPOINT = "/users/feed/add";
	public static final String TOPIC_ROOT_PATH = "/topic";
	public static final String TOPIC_ACTIVITY_FEED_PATH = "/topic/actions";
	public static final String WEBAPP_PREFIX_PATH = "/api";

	public static final String SOCKJS_CLIENT_LIB = "//cdn.jsdelivr.net/sockjs/1.0.2/sockjs.min.js";
	
    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint(ACTIVITY_FEED_ENDPOINT)
        	.withSockJS()
        	.setClientLibraryUrl(SOCKJS_CLIENT_LIB);
    }

    @Override
    public void configureClientInboundChannel(
        final ChannelRegistration registration) {
    }

    @Override
    public void configureClientOutboundChannel(
        final ChannelRegistration registration) {
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(WEBAPP_PREFIX_PATH);
        registry.enableSimpleBroker(TOPIC_ROOT_PATH);
    }
}