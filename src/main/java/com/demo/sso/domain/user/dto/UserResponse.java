package com.demo.sso.domain.user.dto;

import com.demo.sso.domain.user.domain.Gender;
import com.demo.sso.domain.user.domain.Language;
import com.demo.sso.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponse {

    @NotBlank
    @Schema(description = "회원 ID(Sequence)", example = "1")
    private Long id;
    @NotBlank
    @Schema(description = "회원 계정", example = "gildong123")
    private String account;
    @Schema(description = "카카오 계정", example = "gildong@kakao.com")
    private String kakaoAccount;


    @Schema(description = "회원 이름", example = "홍길동", nullable = true)
    private String name;
    @Schema(description = "회원 별명", example = "홍길동 아님", nullable = true)
    private String nickname;

    @NotBlank
    @Schema(description = "회원 폰 번호", example = "010-1234-5678")
    private String phone;
    @Schema(description = "회원 성별", example = "남", nullable = true)
    private Gender gender;
    @Schema(description = "회원 키(cm)", example = "173", nullable = true)
    private Integer height;
    @Schema(description = "회원 출생년월", example = "2000-02-02", nullable = true)
    private LocalDate birthYear;
    @Schema(description = "회원 선호 언어", example = "ko", nullable = true)
    private Language language;
    @Schema(description = "알람 허용 여부", example = "true", nullable = true)
    private Boolean alarmAvailable;
    @Schema(description = "회원 활성 여부", example = "true", nullable = true)
    private Boolean isActive;
    @Schema(description = "회원 소속 병원 이름", example = "길병원", nullable = true)
    private String hospital;
    @Schema(description = "FCM 토큰", example = "dnekjfsbkkjdnqkdk")
    private String pushToken;


    public UserResponse(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.kakaoAccount = user.getKakaoAccount();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phone = user.getPhone();
        this.gender = user.getGender();
        this.height = user.getHeight();
        this.birthYear = user.getBirthYear();
        this.language = user.getLanguage();
        this.alarmAvailable = user.getAlarmAvailable();
        this.isActive = user.getIsActive();
        this.hospital = user.getHospital() != null ? user.getHospital().getName() : null;
        this.pushToken = user.getPushToken();
    }
}
