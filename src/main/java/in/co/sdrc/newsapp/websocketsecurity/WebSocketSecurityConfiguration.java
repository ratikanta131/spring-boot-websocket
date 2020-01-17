package in.co.sdrc.newsapp.websocketsecurity;

import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.stereotype.Component;

//@Component
public class WebSocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {

		messages.nullDestMatcher().authenticated().simpDestMatchers("/queue/errors").permitAll();
//		messages.anyMessage().permitAll();
	}

	// disable csrf
	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}
}
