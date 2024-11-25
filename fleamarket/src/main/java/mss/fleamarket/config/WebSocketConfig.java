package mss.fleamarket.config;

import lombok.RequiredArgsConstructor;
import mss.fleamarket.config.handler.ChatWebSocketHandler;
import mss.fleamarket.config.handler.CommentWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final CommentWebSocketHandler commentWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/chat/{itemId}").setAllowedOrigins("*");
        registry.addHandler(commentWebSocketHandler, "/comment/{itemId}").setAllowedOrigins("*");
    }
}