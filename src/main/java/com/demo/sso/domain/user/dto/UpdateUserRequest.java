package com.demo.sso.domain.user.dto;

import com.demo.sso.domain.user.domain.Gender;
import com.demo.sso.domain.user.domain.Language;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

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
    private Gender gender;
    @Schema(description = "회원 키(cm)", example = "173")
    private Integer height;
    @Schema(description = "회원 출생년월", example = "2000-02-02")
    private LocalDate birthYear;

    @Schema(description = "회원 선호 언어", example = "ko")
    private Language language;
    @Schema(description = "회원 활성 상태", example = "true")
    private Boolean isActive;
    @Schema(description = "회원 알람 허용 여부", example = "true")
    private Boolean alarmAvailable;
}
