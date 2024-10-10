package com.demo.sso.global.exception;

import lombok.Getter;

import static com.demo.sso.global.exception.CustomResponseCode.*;

@Getter
public class ErrorResponse {
    private final String detail;
    private final int code;

    private ErrorResponse(String detail, CustomResponseCode code) {
        this.detail = detail;
        this.code = code.getCode();
    }
    public static ErrorResponse from(String message) {
        return new ErrorResponse(message, INTERNAL_SERVER_ERROR);
    }

    public static ErrorResponse of(String message, CustomResponseCode code) {
        return new ErrorResponse(message, code);
    }
}
