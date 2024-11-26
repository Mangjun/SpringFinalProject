package mss.fleamarket.service.login;

import lombok.RequiredArgsConstructor;
import mss.fleamarket.config.data.CustomUserDetails;
import mss.fleamarket.domain.Member;
import mss.fleamarket.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username).get(0);

        return new CustomUserDetails(
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                List.of() // 권한 정보
        );
    }
}
