package mss.fleamarket.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name; // 이름
    private String email; // 이메일
    private String password; // 비밀번호
    private String phone; // 전화번호

    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>(); // 등록한 상품들

    @OneToMany(mappedBy = "member")
    private List<MemberItem> memberItems = new ArrayList<>(); // 경매에 참가한 상품들

    public Member(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    /* 연관 관계 메소드 */
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
