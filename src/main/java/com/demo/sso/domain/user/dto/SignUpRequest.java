package com.demo.sso.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "이름을 반드시 입력해주세요")
    @Schema(
            description = "이름",
            example = "홍길동")
    private String name;

    @NotBlank(message = "로그인 할 ID를 반드시 입력해주세요")
    @Schema(
            description = "로그인 ID",
            example = "gildong123")
    private String account;

    @NotBlank(message = "별명을 반드시 입력해주세요")
    @Schema(
            description = "별명",
            example = "GilBro")
    private String nickname;

    @NotBlank(message = "비밀번호를 반드시 입력해주세요")
    @Schema(
            description = "비밀번호",
            example = "1234")
    private String password;

    @NotBlank(message = "전화번호를 반드시 입력해주세요")
    @Schema(
            description = "전화번호",
            example = "010-1234-5678")
    private String phone;
}
