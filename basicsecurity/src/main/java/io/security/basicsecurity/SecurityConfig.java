package io.security.basicsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
/*
* WebSecurityConfiguration.class, SpringWebMvcImportSelector.class, OAuth2ImportSelector.class,HttpSecurityConfiguration.class 설정 클래스 임포트함
* 웹 보안 활성화 시킴
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http //인가 정책 설정
                .authorizeHttpRequests() //요청에 대한 보안 검사 실행
                .anyRequest().authenticated(); //어떠한 요청에도 인증을 받도록 설정
        http
                .formLogin(); // formLogin 인증 방식을 사용하도록 설정
        http
                .sessionManagement()
                .sessionFixation().newSession() // 사용자가 인증 성공하면 새로운 세션이 생성됨, 이전 세션에 사용하던 세션정보를 사용할 수 없음
                //.sessionFixation().changeSessionId() // 사용자가 인증 성공하면, 기존 사용자의 세션에 sessionId만 변경됨, 3.1 이상
                //.sessionFixation().migrateSession() // 사용자가 인증 성공하면, 기존 사용자의 세션에 sessionId만 변경됨, 3.1 이하
                //.sessionFixation().none() //세션 고정 보호를 하지 않음
        ;
    }
}
