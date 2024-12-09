package mss.fleamarket.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import mss.fleamarket.domain.Item;
import mss.fleamarket.domain.Member;
import mss.fleamarket.domain.MemberItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberItemRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(MemberItem memberItem) {
        if (memberItem.getId() == null) {
            em.persist(memberItem);
        } else {
            em.merge(memberItem);
        }
    }

    public List<MemberItem> findById(Member member, Item item) {
        return em.createQuery("select mi from MemberItem mi where mi.item = :item and mi.member = :member", MemberItem.class)
                .setParameter("member", member)
                .setParameter("item", item)
                .getResultList();
    }

    public List<MemberItem> findByItem(Item item) {
        return em.createQuery("select mi from MemberItem mi where mi.item = :item", MemberItem.class)
                .setParameter("item", item)
                .getResultList();
    }
}
