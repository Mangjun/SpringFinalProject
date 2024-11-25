package mss.fleamarket.service.chat;

import mss.fleamarket.domain.Chat;
import mss.fleamarket.domain.Item;

import java.util.List;

public interface ChatService {
    void sendMessage(Chat chat);
    List<Chat> getChats(Item item);
}
