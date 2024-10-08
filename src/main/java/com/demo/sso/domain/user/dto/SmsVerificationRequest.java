package com.demo.sso.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SmsVerificationRequest {
    @NotBlank(message = "문자 인증 코드를 반드시 넘겨주세요")
    @Pattern(
            regexp =  "^\\d{6}$",
            message = "인증번호는 반드시 6자리 숫자이어야 합니다.."
    )
    @Schema(
            description = "번호",
            nullable = false,
            example = "001122")
    private String code;
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
}
