package psn.ifplusor.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket//开启websocket
public class WebSocketConfig implements WebSocketConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
	
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	logger.debug("in websockethandler");
    	
        registry.addHandler(new EchoWebSocketHandler(),"/echo").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*"); //支持websocket 的访问链接
        registry.addHandler(new EchoWebSocketHandler(),"/sockjs/echo").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*").withSockJS(); //不支持websocket的访问链接
    }
    
    @Bean
    public WebSocketHandler webSocketHandler(){
        return new EchoWebSocketHandler();
    }
}