package in.co.sdrc.newsapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

// @Configuration
// @EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {
	
	
	@Value("${spring.rabbitmq.host}")
	private String rabbitMQHost;
	
	@Value("${spring.rabbitmq.port}")
	private String rabbitMQPort;
	
	@Value("${spring.rabbitmq.username}")
	private String rabbitMQUserName;
	
	@Value("${spring.rabbitmq.password}")
	private String rabbitMQPassword;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// registry.addEndpoint("/socket")
		// .setAllowedOrigins("*").withSockJS();
	}


	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {

		
		// registry.setApplicationDestinationPrefixes("/app")
		
		// .enableStompBrokerRelay("/queue")
		// .setRelayHost(rabbitMQHost)
		// .setRelayPort(Integer.parseInt(rabbitMQPort))
		// .setClientLogin(rabbitMQUserName)
		// .setClientPasscode(rabbitMQPassword)
		// .setSystemLogin(rabbitMQUserName)
		// .setSystemPasscode(rabbitMQPassword);
	
	}
}
