package com.demo.sso.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String name;
    private String account;
    private String nickname;
    private String password;
    private String phone;
}
