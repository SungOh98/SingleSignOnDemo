package com.demo.sso.global.auth.exception;

public class UnAuthorizationException  extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "권한이 없습니다..";

    private UnAuthorizationException(String message) {
        super(message);

    }
    public static UnAuthorizationException withDetail(String detail) {
        return new UnAuthorizationException(DEFAULT_MESSAGE + "\n 세부정보: " + detail);
    }
}
