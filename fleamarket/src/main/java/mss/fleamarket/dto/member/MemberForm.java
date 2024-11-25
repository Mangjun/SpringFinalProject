package mss.fleamarket.dto.member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberForm {
    private String name;
    private String email;
    private String password;
    private String phone;
}
