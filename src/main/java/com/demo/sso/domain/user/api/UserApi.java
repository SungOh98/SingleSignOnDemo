package com.demo.sso.domain.user.api;

import com.demo.sso.domain.user.dto.*;
import com.demo.sso.global.exception.ErrorResponse;
import com.demo.sso.global.exception.SuccessResponse;
import com.demo.sso.global.infra.kakao.KakaoLoginParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
    @Operation(summary = "카카오 로그인 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "로그인 성공")
    )
    @PostMapping("login/kakao")
    ResponseEntity<KakaoLoginResponse> kakaoLogin(@RequestBody KakaoLoginParams params);

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
    ResponseEntity<SuccessResponse> logout(
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

    @Operation(summary = "문자 전송 요청 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "문자 전송 성공")
    )
    @PostMapping("sms")
    ResponseEntity<SuccessResponse> sms(@RequestBody @Valid SmsRequest request) throws Exception;

    @Operation(summary = "문자 인증 API")
    @ApiResponses(value = {
            // 성공시
            @ApiResponse(responseCode = "200", description = "문자 인증 성공"),
            // 실패시
            @ApiResponse(responseCode = "401", description = "문자 인증 실패"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("sms/verification")
    ResponseEntity<SuccessResponse> verifySms(@RequestBody @Valid SmsVerificationRequest request);

}
