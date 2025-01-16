package com.demo.sso.domain.user.dto;

import com.demo.sso.domain.user.domain.Gender;
import com.demo.sso.domain.user.domain.Language;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "회원 가입 ID를 반드시 입력해주세요")
    @Schema(
            description = "회원 ID",
            example = "gildong123")
    private String account;

    @Null(message = "카카오로 회원 가입이라면 입력해주세요")
    @Schema(
            description = "카카오 계정",
            example = "gildong@kakao.com")
    private String kakaoAccount;

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


    @NotBlank(message = "회원 종류를 반드시 입력해주세요")
    @Schema(
            description = "회원 종류 (doctor, patient, ...)",
            example = "doctor"
    )
    private String userType;

    @NotBlank(message = "회원의 소속 어플리케이션을 반드시 입력해주세요")
    @Schema(
            description = "어플리케이션명 (dialysis, ...)",
            example = "dialysis"
    )
    private String application;


    @Schema(
            nullable = true,
            description = "이름",
            example = "홍길동")
    private String name;
    @Schema(
            nullable = true,
            description = "별명",
            example = "GilBro")
    private String nickname;
    @Schema(
            nullable = true,
            description = "출생년도",
            example = "2000-01-01")
    private LocalDate birthYear;
    @Schema(
            nullable = true,
            description = "성별",
            example = "남")
    private Gender gender;
    @Schema(
            nullable = true,
            description = "키(cm)",
            example = "198")
    private Integer height;
    @Schema(
            nullable = true,
            defaultValue = "ko",
            description = "지원 언어",
            example = "ko"
    )
    private Language language;


}
