package mss.fleamarket.service.item;

import lombok.RequiredArgsConstructor;
import mss.fleamarket.domain.Item;
import mss.fleamarket.domain.Member;
import mss.fleamarket.domain.MemberItem;
import mss.fleamarket.domain.status.ItemStatus;
import mss.fleamarket.repository.ItemRepository;
import mss.fleamarket.repository.MemberItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MemberItemRepository memberItemRepository;

    @Override
    @Transactional
    public void registerItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    @Transactional
    public void modifyItem(Item item) {
        itemRepository.update(item);
    }

    @Override
    @Transactional
    public void bid(MemberItem memberItem) {
        memberItemRepository.save(memberItem);
    }

    @Override
    public MemberItem getMemberItem(Member member, Item item) {
        List<MemberItem> find = memberItemRepository.findById(member, item);
        if (find.isEmpty()) {
            return null;
        } else {
            return find.get(0);
        }
    }

    @Override
    public Item getItem(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getItemsByStatus(ItemStatus status) {
        return itemRepository.findAllOrderByStatus(status);
    }

    @Override
    public List<Item> getItemsByDate(boolean orderBy) {
        /* 1이면 오름차순 */
        if (orderBy) {
            return itemRepository.findAllOrderByAsc();
        }
        /* 0이면 내림차순 */
        else {
            return itemRepository.findAllOrderByDesc();
        }
    }

    @Override
    public List<Item> getItemsByAuction(Member member) {
        return itemRepository.findAllByAuction(member);
    }

    @Override
    public List<Item> getItemsByMember(Member member) {
        return itemRepository.findAllByMember(member);
    }

    @Override
    public int getParticipantCount(Item item) {
        return memberItemRepository.findByItem(item).size();
    }
}
