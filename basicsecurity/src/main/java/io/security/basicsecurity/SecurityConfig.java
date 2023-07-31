package io.security.basicsecurity;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@Configuration
/*
* WebSecurityConfiguration.class, SpringWebMvcImportSelector.class, OAuth2ImportSelector.class,HttpSecurityConfiguration.class 설정 클래스 임포트함
* 웹 보안 활성화 시킴
 */
@EnableWebSecurity
@Order(0) //Order(1)이면 SecurityConfig2가 먼저 실행돼서 /admin에 인증 없이 접근 가능하다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/admin/**") // 내부적으로 AntPathRequestMatcher 가 생성된다.
                .authorizeRequests()// 인증 요청
                .anyRequest().authenticated() // 인증 받아야함
                .and()
                .httpBasic();

    }
}

@Configuration(proxyBeanMethods = false)
@Order(1)
class SecurityConfig2 extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 딱히 설정을 안해서 AnyRequestMatcher 가 생성된다.
                .authorizeRequests()
                .anyRequest().permitAll() //인증 받지 않아도 접근 가능
                .and()
                .formLogin(); //인증 방식은 form 로그인 방식
    }
}
