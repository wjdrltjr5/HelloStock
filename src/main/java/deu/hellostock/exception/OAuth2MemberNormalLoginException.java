package deu.hellostock.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

public class OAuth2MemberNormalLoginException extends InternalAuthenticationServiceException {
    public OAuth2MemberNormalLoginException(String msg) {
        super(msg);
    }

    public OAuth2MemberNormalLoginException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
