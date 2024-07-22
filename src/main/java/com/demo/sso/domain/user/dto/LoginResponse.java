package com.demo.sso.domain.user.dto;

import com.demo.sso.global.auth.jwt.AuthTokens;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;

    public LoginResponse(AuthTokens tokens) {
        this.accessToken = tokens.getAccessToken();
        this.refreshToken = tokens.getRefreshToken();
    }
}
