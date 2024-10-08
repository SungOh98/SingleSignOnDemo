package com.demo.sso.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SuccessResponse {
    @Schema(example = "success")
    private final String message;
    private SuccessResponse(String detail) {
        this.message = detail;
    }

    public static SuccessResponse from(String message) {
        return new SuccessResponse(message);
    }
}
