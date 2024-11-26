package mss.fleamarket.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import mss.fleamarket.domain.Item;
import mss.fleamarket.domain.status.ItemStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Item item) {
        em.persist(item);
    }

    public void update(Item item) {
        em.merge(item);
    }

    public void delete(Item item) {
        em.remove(item);
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    public List<Item> findAllOrderByStatus(ItemStatus status) {
        return em.createQuery("select i from Item i where i.status = :status", Item.class)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Item> findAllOrderByDesc() {
        return em.createQuery("select i from Item i order by i.createdAt desc", Item.class)
                .getResultList();
    }

    public List<Item> findAllOrderByAsc() {
        return em.createQuery("select i from Item i order by i.createdAt", Item.class)
                .getResultList();
    }
}



