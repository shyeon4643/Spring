package io.security.basicsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http //인가 정책 설정
                .authorizeHttpRequests() //요청에 대한 보안 검사 실행
                .anyRequest().authenticated(); //어떠한 요청에도 인증을 받도록 설정
        http
                .formLogin(); // formLogin 인증 방식을 사용하도록 설정
        http
                .logout() // 로그아웃 처리
                .logoutUrl("/logout") //로그아웃 처리 url
                .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동페이지
                .addLogoutHandler(new LogoutHandler() { // 로그아웃 핸들러
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        HttpSession session = request.getSession();
                        session.invalidate();
                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() { // 로그아웃 성공 후 핸들러
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.sendRedirect("/login");
                    }
                })
                .deleteCookies("remeber-me") // 로그아웃 후 쿠키 삭제
                ;
    }
}
