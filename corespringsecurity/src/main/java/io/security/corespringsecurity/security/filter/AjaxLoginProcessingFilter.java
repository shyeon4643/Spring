package io.security.corespringsecurity.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.security.corespringsecurity.domain.AccountDto;
import io.security.corespringsecurity.security.token.AjaxAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    //
    private ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login")); //사용자가 이 url로 접근했을때 매칭이되면 필터가 작동하도록 함
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if(!isAjax(request)){ //ajax방식이 아니면 예외처리
            throw new IllegalStateException("Authentication is not supported");
        }


        AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);
        if(StringUtils.isEmpty(accountDto.getUsername()) || StringUtils.isEmpty(accountDto.getPassword())){ //값이 없거나 password가 값이 없으면 인증하면 안되기 때문에 예외발생 처리
            throw new IllegalArgumentException("Username or Passoword is empty");
        }

        //AuthenticationManager에게 인증 객체(AjaxAuthenticationToken) 전달
        //인증 받기 전 이므로 AjaxAuthenticationToken의 첫 번째 생성자를 사용해 AjaxAuthenticationToken 객체를 생성해 AuthenticationManager에게 인증 처리를 위임
        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(accountDto.getUsername(), accountDto.getPassword());

        return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
    }

    private boolean isAjax(HttpServletRequest request) {

        //ajax 검사 기준은 자유로움
        //여기서는 "XMLHttpRequest"와 헤드 값이 일치할때 ajax 요청
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            return true;
        }
        return false;
    }
}

/*
- addFilterBefore() : 추가하고자 하는 필터를 기존 필터 앞에 위치시키고자 할 때
- addFilter() : 추가하고자 하는 필터를 마지막에 위치시킬 때
- addFilterAfter() : 기존 필터 다음에 위치시키고자 할 때
- addFilterAt() : 기존 필터 위치를 대체하고자 할 때
* */