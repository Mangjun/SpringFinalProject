package mss.fleamarket.service.comment;

import mss.fleamarket.domain.Comment;
import mss.fleamarket.domain.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public void sendMessage(Comment comment) {

    }

    @Override
    public List<Comment> getComments(Item item) {
        return List.of();
    }
}
