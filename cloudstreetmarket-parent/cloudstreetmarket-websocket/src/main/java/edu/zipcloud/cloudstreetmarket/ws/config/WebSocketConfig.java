package edu.zipcloud.cloudstreetmarket.ws.config;

import static edu.zipcloud.cloudstreetmarket.shared.util.Constants.*;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import edu.zipcloud.cloudstreetmarket.shared.util.Constants;

@ComponentScan(value={"edu.zipcloud.cloudstreetmarket.ws","edu.zipcloud.cloudstreetmarket.shared"})
@EnableScheduling
@EnableAsync
@EnableRabbit
@PropertySource(value={"file:${user.home}/app/cloudstreetmarket.properties"})
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession> {

    @Value("${realm.name}")
    private String realmName = "cloudstreetmarket.com";
	
    @Value("${configured.protocol}")
    private String protocol = "http://";
    
	public static final String USER_ROOT_PATH = "/user";
	public static final String TOPICS_ROOT_PATH = "/topic";
	public static final String QUEUES_ROOT_PATH = "/queue";
	public static final String APPLICATION_PREFIX_PATH = "/app";
	
	@Override
	protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(ACTIVITY_FEED_ENDPOINT)
        	.setAllowedOrigins(protocol.concat(realmName))
        	.withSockJS()
        	.setClientLibraryUrl(Constants.SOCKJS_CLIENT_LIB);
    
	    registry.addEndpoint(PRIVATE_STOCKS_ENDPOINT)
	    	.setAllowedOrigins(protocol.concat(realmName))
	    	.withSockJS()
			.setClientLibraryUrl(Constants.SOCKJS_CLIENT_LIB);
	}

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay(TOPICS_ROOT_PATH, QUEUES_ROOT_PATH);
        registry.setApplicationDestinationPrefixes(APPLICATION_PREFIX_PATH);
    }
   
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.taskExecutor().corePoolSize(Runtime.getRuntime().availableProcessors() *4);
	}

	@Override
	//Increase number of threads for slow clients
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.taskExecutor().corePoolSize(Runtime.getRuntime().availableProcessors() *4);
	}
	
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		registration
			.setSendTimeLimit(15*1000) //max time allowed when sending
			.setSendBufferSizeLimit(512*1024); //set 0 to disable buffering
	}
}