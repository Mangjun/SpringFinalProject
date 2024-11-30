package mss.fleamarket.service.member;

import mss.fleamarket.domain.Member;

import java.util.List;

public interface MemberService {
    void joinMember(Member member); // 회원가입

    Member getMember(Long id); // id로 회원 검색
    Member getMemberByEmail(String email); // 이메일로 회원 검색
}