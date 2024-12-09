package mss.fleamarket.dto.auction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuctionUpdate {
    private int participantCount; // 참가자
    private int highestBid; // 입찰가
    private String message; // 메시지
}
