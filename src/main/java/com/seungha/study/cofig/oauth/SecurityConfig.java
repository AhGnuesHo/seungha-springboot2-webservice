package com.seungha.study.cofig.oauth;

import com.seungha.study.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // 스프링시큐리티의 설정을 활성화 시켜준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기위해 비활성화
                .and()
                    .authorizeRequests() // URL별 권한 관리를 설정하는 옵션
                    .antMatchers("/", "/css/**","/images/**",
                        "/js/**","/h2-console/**").permitAll() // permitAll : 전체열람가능
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // "/api/v1/**"는 USER만 허용
                .anyRequest() // 설정된 값 이외 나머지 URL
                .authenticated() // anyRequest()로 인해 나머지 url들은 모두 인증된 사용자만 허용
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // 로그아웃 성공시 / 로 이동
                .and()
                    .oauth2Login()
                        .userInfoEndpoint() // 로그인 성공 후 사용자 정보 가져오기
                            .userService(customOAuth2UserService); // 소셜 로그인 성공 후, 추가로 진행하고자 하는 기능을 명시
    }
}
