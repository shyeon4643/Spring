package io.security.corespringsecurity.security.handler;

import io.security.corespringsecurity.domain.Account;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
인증 성공한 사용자가 서버자원에 접근가능한 권한이랑 사용자의 권한이 일치하지 않을때 인가예외가 발생하는데, 인가 예외는 인증필터가 아닌 FilterSecurityInterceptor에서 인가 예외를 처리한다.
인가예외가 발생하면 필터가 AccessDeniedException에서 예외를 받아서 다시 예외를 throw하게 되는데 이 예외를 AccessDeniedException이 받아서 accessDeniedHandler를 호출한 후에 예외를 처리한다.

AccessDeniedHandler 구현체를 만들어서 설정클래스에 설정하면 SpringSecurity가 만들어준 accessDeniedHandler를 호출해서 관련된 작업들을 처리할 수 있다.
* */

@Component
public class FormAccessDeniedHandler implements AccessDeniedHandler {

    private String errorPage;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        //현재 사용자가 접근하지 못한다는 메시지를 페이지로 만들어서 뿌려줌
        String deniedUrl = errorPage + "?exception=" + accessDeniedException.getMessage();
        redirectStrategy.sendRedirect(request, response, deniedUrl);

    }

    public void setErrorPage(String errorPage) {
        if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }

        this.errorPage = errorPage;
    }

}
