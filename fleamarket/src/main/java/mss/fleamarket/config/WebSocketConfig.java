package mss.fleamarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // 메시지를 구독할 prefix
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 보낼 메시지 prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/auction").withSockJS(); // WebSocket 엔드포인트
    }
}