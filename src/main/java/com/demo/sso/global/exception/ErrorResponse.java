package com.demo.sso.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String detail;
    private ErrorResponse(String detail) {
        this.detail = detail;
    }

    public static ErrorResponse from(String message) {
        return new ErrorResponse(message);
    }
}
