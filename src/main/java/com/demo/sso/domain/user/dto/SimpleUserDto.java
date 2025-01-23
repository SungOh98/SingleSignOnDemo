package com.demo.sso.domain.user.dto;

import com.demo.sso.domain.user.domain.Gender;
import com.demo.sso.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SimpleUserDto {
    @Schema(description = "회원 ID(Sequence)", example = "1")
    private Long id;
    @Schema(description = "회원 이름", example = "홍길동")
    private String name;
    @Schema(description = "회원 출생년월", example = "2000-02-02")
    private LocalDate birthYear;
    @Schema(description = "회원 휴대폰 번호", example = "010-1234-5678")
    private String phone;
    @Schema(description = "회원 성별", example = "남")
    private Gender gender;

    public SimpleUserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.birthYear = user.getBirthYear();
        this.phone = user.getPhone();
        this.gender = user.getGender();
    }
}
