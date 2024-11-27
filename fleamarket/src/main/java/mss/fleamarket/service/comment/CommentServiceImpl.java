package mss.fleamarket.service.comment;

import lombok.RequiredArgsConstructor;
import mss.fleamarket.domain.Comment;
import mss.fleamarket.domain.Item;
import mss.fleamarket.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public void sendMessage(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(Item item) {
        return commentRepository.findAll(item);
    }
}
