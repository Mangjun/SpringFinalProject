package mss.fleamarket.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberItem {

    @Id @GeneratedValue
    @Column(name = "member_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int price; // 등록 금액

    public MemberItem(Member member, Item item, int price) {
        this.member = member;
        this.item = item;
        this.price = price;
    }

    /* 연관 관계 메소드 */

    /**
     * 등록 금액 변경
     * @param price 가격
     */
    public void setPrice(int price) {
        this.price = price;
    }
}
