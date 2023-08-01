package io.security.corespringsecurity.security.common;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

//사용자가 전달하는 추가적인 파라미터를 저장하는 클래스
public class FormWebAuthenticationDetails extends WebAuthenticationDetails {

    private String secretKey;

    public FormWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // 클라이언트로부터 전달받은 값의 데이터 이름 설정하여  secretKey에 저장
        secretKey = request.getParameter("secret_key");
    }

    public String getSecretKey() {
        return secretKey;
    }
}