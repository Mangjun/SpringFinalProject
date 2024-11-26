package mss.fleamarket.service.item;

import lombok.RequiredArgsConstructor;
import mss.fleamarket.domain.Item;
import mss.fleamarket.domain.status.ItemStatus;
import mss.fleamarket.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public void registerItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void modifyItem(Item item) {
        itemRepository.update(item);
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

}
