package com.demo.sso.global.auth.exception;

public class TooManyRequestException extends RuntimeException {
    private static String DEFAULT_MESSAGE = "짧은 시간동안 너무 많은 요청을 하셨습니다. 10분 후에 다시 시도해주세요.";

    private TooManyRequestException(String message) {
        super(message);
    }

    public static TooManyRequestException withDetail(String detail) {
        return new TooManyRequestException(DEFAULT_MESSAGE + "\n 세부정보: " + detail);
    }
}
