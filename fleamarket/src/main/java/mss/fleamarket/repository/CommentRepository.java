package mss.fleamarket.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import mss.fleamarket.domain.Comment;
import mss.fleamarket.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public List<Comment> findAll(Item item) {
        return em.createQuery("select c from Comment c where c.item = :item", Comment.class)
                .setParameter("item", item)
                .getResultList();
    }
}
