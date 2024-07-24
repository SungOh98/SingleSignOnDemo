package com.demo.sso.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRefreshRequest {

    @NotBlank(message = "Access Token을 넘겨주세요.")
    @Schema(
            description = "Access Token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String accessToken;


    @NotBlank(message = "Refresh Token을 넘겨주세요.")
    @Schema(
            description = "Refresh Token",
            example = "wdad2jjfi/dw20fwdo3r411-1")
    private String refreshToken;
}
