package mss.fleamarket.service.member;

import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import mss.fleamarket.domain.Member;
import mss.fleamarket.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    @Synchronized
    public void joinMember(Member member) {
        String encodePassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodePassword);

        memberRepository.save(member);
    }

    @Override
    public Member getMember(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).get(0);
    }
}
