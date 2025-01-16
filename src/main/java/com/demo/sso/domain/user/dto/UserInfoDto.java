package com.demo.sso.domain.user.dto;

import com.demo.sso.domain.user.domain.Gender;
import com.demo.sso.global.infra.kakao.KakaoInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Data
public class UserInfoDto {
    public UserInfoDto(KakaoInfoResponse kakaoInfoResponse) {
        this.account = String.valueOf(UUID.randomUUID());
        this.kakaoAccount = kakaoInfoResponse.getAccount();
        this.name = kakaoInfoResponse.getName();
        this.nickname = kakaoInfoResponse.getNickName();
        this.password = String.valueOf(UUID.randomUUID());
        this.phone = initPhone(kakaoInfoResponse.getPhoneNumber());
        this.gender = initGender(kakaoInfoResponse.getGender());
        this.birthyear = initBirthYear(kakaoInfoResponse.getBirthyear(), kakaoInfoResponse.getBirthday());
    }

    private LocalDate initBirthYear(String year, String day) {
        if (year == null || day == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(year + day, formatter);
    }

    private Gender initGender(String gender) {
        Gender gen = null;
        if (gender != null) {
            if (gender.equals("male")) gen = Gender.남;
            else gen = Gender.여;
        }
        return gen;
    }

    private String initPhone(String phone) {
        if (phone == null) return null;
        return phone.replace("+82 ", "0");
    }

    @NotBlank(message = "계정은 서버에서 랜덤 생성.")
    @Schema(
            description = "계정",
            example = "daledneffkdad"
    )
    private String account;

    @NotBlank(message = "카카오 계정은 반드시 얻음.")
    @Schema(
            description = "카카오 계정",
            example = "gildong123@kakao.com"
    )
    private String kakaoAccount;

    @Null
    @Schema(
            description = "사용자 이름",
            example = "홍길동"
    )
    private String name;
    @NotBlank(message = "닉네임은 반드시 얻음")
    @Schema(
            description = "사용자 별명",
            example = "고길동아님"
    )
    private String nickname;
    @NotBlank(message = "비밀번호도 반드시 존재")
    @Schema(
            description = "UUID로 생성된 사용자 비밀번호",
            example = "daledneffkdad"
    )
    private String password;
    @Null
    @Schema(
            description = "사용자 핸드폰 번호",
            example = "010-1234-5678"
    )
    private String phone;

    @Null
    @Schema(
            description = "사용자의 출생 년도",
            example = "1999-01-01"
    )
    private LocalDate birthyear;

    @Null
    @Schema(
            description = "사용자의 성별",
            example = "남"
    )
    private Gender gender;
}
