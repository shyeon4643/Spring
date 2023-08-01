package io.security.corespringsecurity.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class FormAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /*
     requestCache와 RedirectStragey를 사용하여 사용자가 인증 요청 성공시 이전에 접근하려 했던 자원(리소스)의 경로로 바로 보내기 위해서 설정해줍니다.
     이전의 접근하려 했던 자원(리소스)가 없는 경우 null을 반환하도록 하여 setDefaultTargetUrl에 설정해준 url로 보내줍니다.
    * */
    private RequestCache requestCache = new HttpSessionRequestCache(); // 사용자의 이전 접근 자원을 가져옴

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {

        setDefaultTargetUrl("/");

        SavedRequest savedRequest = requestCache.getRequest(request, response); //예외나 로그인 등으로 이전 정보가 없을때 null일 수 있음

        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }

    }

}
