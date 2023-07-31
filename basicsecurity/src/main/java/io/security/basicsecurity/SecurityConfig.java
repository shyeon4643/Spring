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
                .maximumSessions(1) //최대 허용 가능 세션 수, -1 : 무제한 로그인 세션 허용
                .maxSessionsPreventsLogin(true) // 동시 로그인 차단함, false : 기존 세션 만료(default)
                ;
    }
}
