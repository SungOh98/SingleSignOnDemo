package com.demo.sso.domain.user.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String account;
    private String password;

}
