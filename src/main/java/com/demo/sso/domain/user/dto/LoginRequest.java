package com.demo.sso.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "로그인 ID를 반드시 입력해주세요")
    @Schema(
            description = "로그인 ID",
            nullable = false,
            example = "gildong123")
    private String account;

    @NotBlank(message = "비밀번호를 반드시 입력해주세요.")
    @Schema(
            description = "비밀번호",
            nullable = false,
            example = "1234"
    )
    private String password;


    @NotBlank(message = "반드시 로그인 할 어플리케이션을 입력해주세요")
    @Schema(
            description = "로그인 할 어플리케이션",
            example = "heart_on"
    )
    private String application;
}
