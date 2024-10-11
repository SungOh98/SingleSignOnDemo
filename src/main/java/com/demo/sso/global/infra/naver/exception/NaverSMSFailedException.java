package com.demo.sso.global.infra.naver.exception;


public class NaverSMSFailedException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "문자메시지 전송 과정이 실패하였습니다.";

    private NaverSMSFailedException(String message) {
        super(message);

    }
    public static NaverSMSFailedException withDetail(String detail) {
        return new NaverSMSFailedException(DEFAULT_MESSAGE + "\n 세부정보: " + detail);
    }
}
