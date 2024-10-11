package com.demo.sso.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SmsRequest {
    @NotBlank(message = "핸드폰 번호를 반드시 넘겨주세요")
    @Pattern(
            regexp = "^(010-\\d{3,4}-\\d{4})|(02-\\d{3,4}-\\d{4})|(0\\d{2}-\\d{3,4}-\\d{4})$",
            message = "전화번호 형식은 010-XXXX-YYYY 또는 02/0XX-XXX-YYYY 형식이어야 합니다."
    )
    @Schema(
            description = "번호",
            nullable = false,
            example = "010-1234-5678")
    private String phone;


    @NotBlank(message = "어플리케이션 명을 넘겨주세요.")
    @Schema(
            description = "문자메시지에 표시될 어플리케이션 명",
            nullable = false,
            example = "HeartOn")
    private String application;
}
