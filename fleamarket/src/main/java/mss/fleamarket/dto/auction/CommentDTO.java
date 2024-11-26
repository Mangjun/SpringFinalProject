package mss.fleamarket.dto.auction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDTO {
    private String participant; // 참가자
    private int bidAmount; // 입찰가
    private String message; // 메시지
}
