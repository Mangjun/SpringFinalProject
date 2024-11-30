package mss.fleamarket.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mss.fleamarket.domain.status.ItemStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String title; // 제목
    private String photo; // 사진
    private String description; // 설명
    private int price; // 시작 가격
    private LocalDateTime createdAt; // 등록 시간

    @Enumerated(EnumType.STRING)
    private ItemStatus status; // 상품 상태 [SALE, SOLDOUT]

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 상품 등록한 회원

    @OneToMany(mappedBy = "item")
    private List<MemberItem> memberItems = new ArrayList<>(); // 경매에 참가한 회원들

    @OneToMany(mappedBy = "item")
    private List<Comment> comments = new ArrayList<>(); // 경매 채팅

    public Item(String title, String photo, String description, int price, Member member) {
        this.title = title;
        this.photo = photo;
        this.description = description;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.status = ItemStatus.SALE;
        this.member = member;
    }

    /* 연관 관계 메소드 */
    public boolean bid(Member member, int bidAmount) {
        if (this.member.getId() == member.getId()) {
            return false;
        }

        if (bidAmount >= price + 100) {
            this.price = bidAmount;
            return true;
        } else {
            return false;
        }
    }

    /* 경매 완료 */
    public void soldOut() {
        this.status = ItemStatus.SOLDOUT;
    }
}