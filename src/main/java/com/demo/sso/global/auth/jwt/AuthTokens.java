package com.demo.sso.global.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthTokens {
    private String accessToken;
    private String refreshToken;
}
