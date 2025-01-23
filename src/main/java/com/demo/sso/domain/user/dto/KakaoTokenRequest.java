package com.demo.sso.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KakaoTokenRequest {
    @NotBlank(message = "반드시 토큰을 입력해주세요.")
    @Schema(
            description = "카카오 서버에서 받은 Access-Token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMj"
    )
    private String token;
    @NotBlank(message = "로그인 할 어플리케이션 명")
    @Schema(
            description = "로그인 할 어플리케이션 명",
            example = "dialysis"
    )
    private String application;
}

