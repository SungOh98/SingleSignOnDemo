package com.demo.sso.global.auth.exception;

public class AccessTokenExpiredException extends RuntimeException {
    private static String DEFAULT_MESSAGE = "Access Token이 만료되었습니다.";

    private AccessTokenExpiredException(String message) {
        super(message);
    }
    public static AccessTokenExpiredException withDetail(String detail) {
        return new AccessTokenExpiredException(DEFAULT_MESSAGE + "\n 세부정보: " + detail);
    }
}
