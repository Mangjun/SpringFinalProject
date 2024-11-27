package mss.fleamarket.service.item;

import mss.fleamarket.domain.Item;
import mss.fleamarket.domain.Member;
import mss.fleamarket.domain.MemberItem;
import mss.fleamarket.domain.status.ItemStatus;

import java.util.List;

public interface ItemService {
    void registerItem(Item item); // 상품 등록
    void modifyItem(Item item); // 상품 수정

    void bid(MemberItem memberItem);
    MemberItem getMemberItem(Member member, Item item);

    Item getItem(Long id); // 상품 조회
    List<Item> getItems(); // 상품 전체 리스트
    List<Item> getItemsByStatus(ItemStatus status); // 상품 상태로 검색
    List<Item> getItemsByDate(boolean orderBy); // 시간 순으로 검색
    List<Item> getItemsByAuction(Member member); // 경매에 참여한 상품 리스트
    List<Item> getItemsByMember(Member member); // 등록한 상품 리스트
    int getParticipantCount(Item item);
}
