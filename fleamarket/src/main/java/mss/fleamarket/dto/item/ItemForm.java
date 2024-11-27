package mss.fleamarket.dto.item;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemForm {
    private String title;
    private String photo;
    private String description;
    private int price;
}
