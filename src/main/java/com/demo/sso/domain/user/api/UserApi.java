package com.demo.sso.domain.user.api;

import com.demo.sso.domain.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "유저관련 API", description = "회원가입, 로그인, 로그아웃, 토큰 리프레시 등등")
public interface UserApi {

//    @Operation(summary = "회원가입 API")
//    @ApiResponses(
//            @ApiResponse(responseCode = "200", description = "회원 가입 성공")
//    )
//    @PostMapping("sign-up")
//    ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest request);

    @Operation(summary = "로그인 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "로그인 성공")
    )
    @PostMapping("login")
    ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request);


    @Operation(summary = "로그아웃 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    )
    @PostMapping("logout")
    ResponseEntity<Void> logout(
            @RequestHeader("Authorization")
            @Schema(
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            )
            String accessToken);

    @Operation(summary = "AccessToken 재발급 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공")
    )
    @PostMapping("refresh")
    ResponseEntity<TokenRefreshResponse> refresh(
            @RequestBody @Valid TokenRefreshRequest request
    );


}
