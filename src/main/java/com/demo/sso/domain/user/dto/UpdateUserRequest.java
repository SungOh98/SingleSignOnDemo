package com.demo.sso.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @Schema(description = "회원 계정", example = "gildong123")
    private String account;
    @Schema(description = "회원 이름", example = "홍길동")
    private String name;
    @Schema(description = "회원 별명", example = "홍길동 아님")
    private String nickname;
    @Schema(description = "회원 폰 번호", example = "010-1234-5678")
    private String phone;
    @Schema(description = "회원 성별", example = "남")
    private String gender;
    @Schema(description = "회원 키(cm)", example = "173")
    private String height;
    @Schema(description = "회원 출생년월", example = "2000-02-02")
    private String birthYear;
}
