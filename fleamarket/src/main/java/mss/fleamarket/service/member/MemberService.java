package mss.fleamarket.service.member;

import mss.fleamarket.domain.Member;

import java.util.List;

public interface MemberService {
    void joinMember(Member member); // 회원가입
    // void modifyMember(Member member); // 정보 수정
    // void removeMember(); // 회원 탈퇴

    Member getMember(Long id); // id로 회원 검색
    // Member getMemberByName(String name); // 이름으로 회원 검색
    Member getMemberByEmail(String email); // 이메일로 회원 검색

    // List<Member> getMembers(); // 회원 리스트
}