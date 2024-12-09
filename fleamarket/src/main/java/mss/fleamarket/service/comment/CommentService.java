package mss.fleamarket.service.comment;

import mss.fleamarket.domain.Comment;
import mss.fleamarket.domain.Item;

import java.util.List;

public interface CommentService {
    void sendMessage(Comment comment);
    List<Comment> getComments(Item item);
}
