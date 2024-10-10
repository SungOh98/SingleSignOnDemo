package com.demo.sso.global.exception;


import lombok.Getter;

@Getter
public enum CustomResponseCode {
    SUCCESS(200),
    WRONG_SMS_MESSAGE(0),
    SMS_MESSAGE_TIMEOUT(1),
    INTERNAL_SERVER_ERROR(500),
    TOO_MANY_SMS_REQUEST(2);

    // 정수를 반환
    private final int code;

    CustomResponseCode(int code) {
        this.code = code;
    }

}
