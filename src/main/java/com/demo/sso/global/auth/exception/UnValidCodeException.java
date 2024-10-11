package com.demo.sso.global.auth.exception;

public class UnValidCodeException extends RuntimeException {
    private static String DEFAULT_MESSAGE = "문자 인증번호를 잘못 입력하셨습니다.";

    private UnValidCodeException(String message) {
        super(message);

    }
    public static UnValidCodeException withDetail(String detail) {
        return new UnValidCodeException(DEFAULT_MESSAGE + "\n 세부정보: " + detail);
    }
}
