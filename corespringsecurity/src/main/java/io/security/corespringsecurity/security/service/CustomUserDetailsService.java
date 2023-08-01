package io.security.corespringsecurity.security.service;

import io.security.corespringsecurity.domain.entity.Account;
import io.security.corespringsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService { //스프링 시큐리티가 데이터베이스와 연동되도록 한다.
/*
* 데이터베이스로부터 사용자의 계정이 존재하는지 검사한다.
* 사용자의 권한 정보를 생성한다.
* UserDetails 구현체인 AccountContext를 생성해 반환한다.
* */
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = userRepository.findByUsername(username); // 사용자 정보가 있으면 Account 객체를 얻음

        if(account == null){ // 사용자 정보가 없으면 예외처리
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        //사용자 권한정보 생성
        //유저 이름으로 계정을 찾은 후, 권한을 SimpleGrantedAuthority로 부여
        List<GrantedAuthority> roles = new ArrayList<>();
        //roles.add(new SimpleGrantedAuthority(account.getRole()));

        //AccountContext라는 곳인데 스프링 기본 User를 가져와서 구현
        AccountContext accountContext = new AccountContext(account, roles);

        return accountContext;
    }
}
