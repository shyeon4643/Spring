package io.security.basicsecurity;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@Configuration
/*
* WebSecurityConfiguration.class, SpringWebMvcImportSelector.class, OAuth2ImportSelector.class,HttpSecurityConfiguration.class 설정 클래스 임포트함
* 웹 보안 활성화 시킴
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN"); // 이렇게 써주면 admin사용자는 user와 sys가 접근 가능한 url에 접근을 못한다.
        //auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN","USER","SYS"); // 이렇게 써주어야지 admin이 user와 sys가 접근 가능한 url에 접근 가능하다
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http //인가 정책 설정
                .authorizeHttpRequests() //요청에 대한 보안 검사 실행
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/admin/pay").hasRole("ADMIN")
                .antMatchers("/admin/**").hasAnyRole("ADMIN","SYS")
                //.antMatchers("/admin/pay").hasRole("ADMIN") // /admin/pay에는 admin만 접근 가능한데 좁은 범위가 넓은 범위보다 뒤에 있어서 sys도 접근 가능하게 된다.
                .anyRequest().authenticated(); //어떠한 요청에도 인증을 받도록 설정
        http
                .formLogin(); // formLogin 인증 방식을 사용하도록 설정

    }
}
