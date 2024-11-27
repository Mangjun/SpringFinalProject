package mss.fleamarket.dto.item;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemDTO {
    private Long id;
    private String title;
    private String photo;
    private String description;
    private int price; // 금액
    private int count; // 참가 인원
}
