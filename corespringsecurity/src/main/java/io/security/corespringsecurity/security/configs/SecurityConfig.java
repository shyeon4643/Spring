package io.security.corespringsecurity.security.configs;

import io.security.corespringsecurity.security.provider.FormAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationDetailsSource formWebAuthenticationDetails;
    @Autowired
    private AuthenticationSuccessHandler formAuthenticationSuccessHandler;
  @Autowired
  private UserDetailsService userDetailsService; //우리가 만든 UserDetailsService 사용

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception{
     auth.authenticationProvider(authenticationProvider());
  }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new FormAuthenticationProvider(passwordEncoder());
    }

    @Override
    //js, css, image 파일 등 보안 필터를 적용할 필요가 없는 리소스를 설정하여 보안필터를 거치지 않고 통과되도록 설정
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/users","/error").permitAll() //webingore과 다르게 permitAll은 보안 필터를 거쳐서 확인함
                .antMatchers("/mypage").hasRole("USER")
                .antMatchers("/messages").hasRole("MANAGER")
                .antMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated()

        .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")
                .authenticationDetailsSource(formWebAuthenticationDetails)//이것을 통해서 formWebAuthenticationDetails 생성
                .successHandler(formAuthenticationSuccessHandler)
                .defaultSuccessUrl("/")
                .permitAll()
                ;
    }
}
