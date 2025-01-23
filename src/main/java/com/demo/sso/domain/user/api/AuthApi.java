package com.demo.sso.domain.user.api;

import com.demo.sso.domain.user.dto.*;
import com.demo.sso.global.auth.jwt.UserOnly;
import com.demo.sso.global.exception.ErrorResponse;
import com.demo.sso.global.exception.SuccessResponse;
import com.demo.sso.global.infra.kakao.KakaoLoginParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "인증 관련 API", description = "여러 어플리케이션 클라이언트가 사용하는 통합 인증 API")
public interface AuthApi {
    @Operation(summary = "로그인 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "로그인 성공")
    )
    ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request);

    @Operation(summary = "카카오 로그인 API", description = "카카오에서 휴대폰 번호를 못 받을 경우 사용.")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "로그인 성공")
    )
    ResponseEntity<KakaoLoginResponse> kakaoLogin(@RequestBody @Valid KakaoLoginParams params);


    @Operation(summary = "카카오 로그인 API V2", description = "카카오에서 휴대폰 번호를 받을 수 있을 경우")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "로그인 성공")
    )
    ResponseEntity<KakaoLoginResponse> kakaoLoginV2(@RequestBody @Valid KakaoLoginParams params);


    @Operation(summary = "카카오 로그인 API(App)")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "로그인 성공")
    )
    ResponseEntity<KakaoLoginResponse> kakaoLoginByApp(@RequestBody KakaoTokenRequest request);

    @Operation(summary = "문자 전송 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문자 전송 성공"),
            // 실패시
            @ApiResponse(responseCode = "429", description = "너무 많은 문자 요청을 할 경우 발생하는 에러"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            // 실패시
            @ApiResponse(responseCode = "500", description = "서버 에러"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
    }
            // 429
    )
    ResponseEntity<SuccessResponse> sms(@RequestBody @Valid SmsRequest request) throws Exception;

    @Operation(summary = "문자 인증 API")
    @ApiResponses(value = {
            // 성공시
            @ApiResponse(responseCode = "200", description = "문자 인증 성공"),
            // 실패시
            @ApiResponse(responseCode = "400", description = "문자 인증 실패"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "410", description = "문자 인증 시간 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "서버 에러"
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
    })
    ResponseEntity<SuccessResponse> verifySms(@RequestBody @Valid SmsVerificationRequest request);


    @Operation(summary = "AccessToken 재발급 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공")
    )
    ResponseEntity<TokenRefreshResponse> refresh(@RequestBody @Valid TokenRefreshRequest request);

    @Operation(summary = "로그아웃 API", security = {@SecurityRequirement(name = "bearerAuth")})
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    )
    ResponseEntity<SuccessResponse> logout(@UserOnly Long userId);

}
