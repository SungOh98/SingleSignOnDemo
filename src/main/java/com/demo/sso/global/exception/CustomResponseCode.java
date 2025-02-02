package com.demo.sso.global.exception;


import lombok.Getter;


@Getter
public enum CustomResponseCode {
    SUCCESS(200),
    WRONG_SMS_MESSAGE(0),
    SMS_MESSAGE_TIMEOUT(1),
    INTERNAL_SERVER_ERROR(500),
    TOO_MANY_SMS_REQUEST(2),
    DUPLICATE_SMS_REQUEST(3),
    ACCESS_TOKEN_TIMEOUT(4),
    REFRESH_TOKEN_TIMEOUT(5),
    INVALID_JWT_TOKEN_TYPE(6);

    // 정수를 반환
    private final int code;

    CustomResponseCode(int code) {
        this.code = code;
    }

}
