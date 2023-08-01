package io.security.corespringsecurity.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {

        // 기본 예외 메시지
        String errorMessage = "Invalid Username or Password";

        // exceprion 처리
        if(exception instanceof BadCredentialsException) {
            errorMessage = "Invalid Username or Password";

        }else if(exception instanceof InsufficientAuthenticationException) {
            errorMessage = "Invalid Secret";
        }

        // 파라미터로 error와 exception을 보내서 controller에서 처리
        setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);
        /*
        * successHandler와 다르게 failureHandler에서 super클래스를 선언해준 이유
            -> 실패와 관련된 여러가지 후속처리를 부모에게 위임하기 위함
            -> 모두 super 클래스를 호출하지 않아도 상관은 없음
        * */
        // 부모클래스의 onAuthenticationFailure로 처리를 위임
        super.onAuthenticationFailure(request, response, exception);

    }
}
