package com.demo.sso.global.infra.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoLoginParams {
    @NotBlank(message = "반드시 인증 코드를 입력해주세요")
    @Schema(
            description = "카카오 서버에서 받은 인증 코드",
            example = "ddbebfkbawkjdbkqwbkd1"
    )
    @JsonProperty("authorization-code")
    private String authorizationCode;
    @NotBlank(message = "반드시 로그인 할 어플리케이션을 입력해주세요")
    @Schema(
            description = "로그인 할 어플리케이션",
            example = "dialysis"
    )
    private String application;

    @JsonProperty("redirect-uri")
    @NotBlank(message = "리다이렉트 URI를 넘겨주세요")
    @Schema(
            description = "카카오 로그인 Redirect URI",
            example = "https://dialysis.livincare.kr/kakao"
    )
    private String redirectUri;
}
