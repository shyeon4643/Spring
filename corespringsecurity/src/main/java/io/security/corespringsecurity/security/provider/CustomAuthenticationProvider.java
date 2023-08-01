package io.security.corespringsecurity.security.provider;

import io.security.corespringsecurity.security.service.AccountContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;


    private PasswordEncoder passwordEncoder;


    public  CustomAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Authentication authenticate(Authentication authentication/*사용자가 입력한 아이디 비번이 담겨 있음*/) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String)authentication.getCredentials();

        //userDetailsService로부터 userDetailsService 타입의 객체를 받아옴
        //아이디 검증
        AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(username);

        //비번 검증
        if(!passwordEncoder.matches(password/*사용자 비밀번호*/, accountContext.getAccount().getPassword()/*DB에 저장된 비밀번호*/)){
            throw new BadCredentialsException("BadCredentialsException"); //비밀번호가 일치하지 않으면 예외
        }

        //검증이 완료되면 provider는 UsernamePasswordAuthenticationToken을 생성한다
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

/*
    // 로그인(아이디와 비밀번호)정보로 저장할때 사용
    public UsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super((Collection)null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    //인증 성공 후에 사용
    public UsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }


 */
}
