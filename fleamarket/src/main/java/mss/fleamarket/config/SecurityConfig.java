package mss.fleamarket.config;

import lombok.RequiredArgsConstructor;
import mss.fleamarket.service.login.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .formLogin() // 로그인 설정
                .loginPage("/login") // 로그인 페이지 설정
                .loginProcessingUrl("/login") // 로그인 폼의 url
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true) // 로그인 성공 시 이동할 url
            .and()
            .logout()
                .logoutUrl("/logout") // 로그아웃 url
                .logoutSuccessUrl("/"); // 로그아웃 성공 시 이동할 url

        return http.build();
    }
}
