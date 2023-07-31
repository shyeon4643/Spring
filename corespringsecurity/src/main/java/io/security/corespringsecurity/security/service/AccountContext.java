package io.security.corespringsecurity.security.service;

import io.security.corespringsecurity.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/*
스프링 시큐리티에서는 UserDetails를 구현한 User 클래스를 제공한다.
이러한 User 클래스를 상속해 최종적으로 UserDetails를 상속 받도록 한다.
* */
public class AccountContext extends User {

    private final Account account;

    public AccountContext(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getUsername(), account.getPassword(), authorities);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
