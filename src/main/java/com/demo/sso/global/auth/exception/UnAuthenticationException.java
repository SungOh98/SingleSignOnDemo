package com.demo.sso.global.auth.exception;

public class UnAuthenticationException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "올바르지 않은 인증 정보입니다.";

    private UnAuthenticationException(String message) {
        super(message);

    }
    public static UnAuthenticationException withDetail(String detail) {
        return new UnAuthenticationException(DEFAULT_MESSAGE + "\n 세부정보: " + detail);
    }
}
