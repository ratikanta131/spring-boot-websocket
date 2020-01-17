package in.co.sdrc.newsapp.websocketsecurity;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//@Component
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//	        registry.addHandler(new MyHandler(), "/myHandler")
//	            .addInterceptors(new HttpSessionHandshakeInterceptor());

		System.out.println("Interceptor called!!!!!!!!!!!!!!!!");
	}

}
