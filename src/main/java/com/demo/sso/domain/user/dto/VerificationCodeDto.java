package com.demo.sso.domain.user.dto;

import lombok.Data;

@Data
public class VerificationCodeDto {

    private String phone;
    private String code;
}
