package com.demo.sso.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRefreshResponse {
    @Schema(
            description = "재발급 받은 Jwt 토큰 형태의 Access Token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String accessToken;


    @Schema(
            description = "재발급 받은 UUID로 구성된 Refresh Token",
            example = "wdad2jjfi/dw20fwdo3r411-1")
    private String refreshToken;
}
