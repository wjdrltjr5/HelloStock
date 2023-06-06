package deu.hellostock.config;

import deu.hellostock.exception.OAuth2MemberNormalLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@Component
@Slf4j
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private HttpSession session;
    private String errorMessage;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {



        session.invalidate();

        log.info("예외 타입 확인 = {}",exception.getClass());
        if(exception instanceof BadCredentialsException){
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다.";
        }else if (exception instanceof OAuth2MemberNormalLoginException) {
            log.info("소셜");
            errorMessage = "소셜 사용자 입니다. 소셜로그인을 이용해 주세요";
        }else if(exception instanceof InternalAuthenticationServiceException){
            log.info("내부적");
            errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요";
        }else if(exception instanceof UsernameNotFoundException){
            errorMessage = "존재하지 않는 계정입니다.";
        }else if(exception instanceof AuthenticationCredentialsNotFoundException){
            errorMessage = "인증 요청이 거부되었습니다 관리자한테 문의";
        }else {
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다.";
            log.info("exception = {}",exception);
        }
        log.info("loginfailed message = {}",errorMessage);
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/signin?errormessage="+errorMessage);
        super.onAuthenticationFailure(request, response, exception); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
}
