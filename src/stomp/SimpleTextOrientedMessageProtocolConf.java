package stomp;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration;

@Configuration
@EnableWebSocketMessageBroker
public class SimpleTextOrientedMessageProtocolConf extends AbstractWebSocketMessageBrokerConfigurer{

	
	
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
	registry.enableStompBrokerRelay("/queue", "/topic").
		setRelayHost("localhost").
		setRelayPort(61613).
		setClientLogin("guest").
		setClientPasscode("guest");
	registry.setApplicationDestinationPrefixes("/app");
	}
	
	

}
