package com.demo.sso.domain.user.dto;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String accessToken;
    private String refreshToken;
}
