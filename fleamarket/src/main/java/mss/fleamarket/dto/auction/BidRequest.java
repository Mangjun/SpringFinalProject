package mss.fleamarket.dto.auction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BidRequest {
    private Long memberId;
    private Long itemId;
    private int bidAmount;
}
