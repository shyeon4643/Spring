package io.security.corespringsecurity.security.provider;

import io.security.corespringsecurity.security.common.FormWebAuthenticationDetails;
import io.security.corespringsecurity.security.service.AccountContext;
import io.security.corespringsecurity.security.token.AjaxAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

/*
 AuthenticationProvider에서 사용할 인증 객체가 UsernamePasswordAuthenticationToken이 아닌 우리가 생성한 Ajax 인증 방식용 인증 객체(AjaxAuthenticationToken)
* */
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;
    public AjaxAuthenticationProvider(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }


    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();

        AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(loginId);
        // AuthenticationProvider는 사용자가 입력한 패스워드와 UserDetails의 패스워드와 비교
        if (!passwordEncoder.matches(password, accountContext.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new AjaxAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());
    }

    @Override
    //전달받은 인증 객체가 AjaxAuthenticationToken 타입과 같을때 동작
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}