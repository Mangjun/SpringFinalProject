package mss.fleamarket.config.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class CommentWebSocketHandler extends TextWebSocketHandler {
}
