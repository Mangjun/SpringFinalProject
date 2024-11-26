package mss.fleamarket.service.item;

import mss.fleamarket.domain.Item;
import mss.fleamarket.domain.status.ItemStatus;

import java.util.List;

public interface ItemService {
    void registerItem(Item item); // 상품 등록
    void modifyItem(Item item); // 상품 수정
    Item getItem(Long id); // 상품 조회
    List<Item> getItems(); // 상품 전체 리스트
    List<Item> getItemsByStatus(ItemStatus status); // 상품 상태로 검색
    List<Item> getItemsByDate(boolean orderBy); // 시간 순으로 검색
}
