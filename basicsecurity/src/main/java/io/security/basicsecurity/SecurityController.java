package io.security.basicsecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SecurityController {
    /*
      인증이 된 후 Authentication 이 저장되는 곳은 SecurityContext 입니다.
      그리고 SecurityContext 가 저장되는 곳이 ThreadLocal 이고 이 역할을 하는 클래스가 SecurityContextHolder 클래스입니다.
      SecurityContext 가 최종 저장되는 곳은 ThreadLocal 이라고 보시면 됩니다.
      다만 HttpSession 이 인증에 성공할 경우에 SecurityContext 를 저장하는 것은 맞지만
      스프링 시큐리티가 결국은 SecurityContextHolder 를 사용해서 HttpSession 에서 SecurityContext를 꺼내어 ThreadLocal 에 다시 저장하고 있기 때문에
      HttpSession 이 ThreadLocal 과 비슷한 역할을 한다고 볼 수는 없을 것 같습니다
    * */
    @GetMapping("/")
    public String index(HttpSession session){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //SecurityContextHolder 저장확인
        SecurityContext context = (SecurityContext)session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY); //HttpSession에 저장 확인
        Authentication authentication1 = context.getAuthentication();

        return "home";
    }

    @GetMapping("/thread")
    public String thread(){ //ThreadLocal 저장 확인

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    }
                }
        ).start();
        return "thread";
    }



}
