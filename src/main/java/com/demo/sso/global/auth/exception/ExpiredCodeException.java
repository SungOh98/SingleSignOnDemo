package com.demo.sso.global.auth.exception;

public class ExpiredCodeException extends RuntimeException {
    private static String DEFAULT_MESSAGE = "메시지 인증 시간이 지났거나, 인증 문자를 보내지 않았습니다.";

    private ExpiredCodeException(String message) {
        super(message);
    }

    public static ExpiredCodeException withDetail(String detail) {
        return new ExpiredCodeException(DEFAULT_MESSAGE + "\n 세부정보: " + detail);
    }
}
