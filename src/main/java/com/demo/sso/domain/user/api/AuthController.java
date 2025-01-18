package com.demo.sso.domain.user.api;

import com.demo.sso.domain.user.dto.*;
import com.demo.sso.domain.user.service.UserService;
import com.demo.sso.global.auth.jwt.AuthTokens;
import com.demo.sso.global.auth.jwt.UserOnly;
import com.demo.sso.global.auth.sms.SmsService;
import com.demo.sso.global.exception.SuccessResponse;
import com.demo.sso.global.infra.kakao.KakaoLoginParams;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class AuthController implements AuthApi{
    private final UserService userService;
    private final SmsService smsService;

    @PostMapping("login/kakao")
    public ResponseEntity<KakaoLoginResponse> kakaoLogin(@RequestBody @Valid KakaoLoginParams params) {
        KakaoLoginResponse kakaoLoginResponse = userService.kakaoLogin(params);
        return ResponseEntity.ok(kakaoLoginResponse);
    }

    @PostMapping("login/kakao/app")
    public ResponseEntity<KakaoLoginResponse> kakaoLoginByApp(@RequestBody KakaoTokenRequest request) {
        KakaoLoginResponse kakaoLoginResponse = userService.kakaoLoginByApp(request);
        return ResponseEntity.ok(kakaoLoginResponse);
    }


    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthTokens tokens = userService.login(request);
        return ResponseEntity.ok(new LoginResponse(tokens));
    }


    @PostMapping("logout")
    public ResponseEntity<SuccessResponse> logout(@UserOnly Long userId) {
        userService.logout(userId);
        return ResponseEntity.ok(SuccessResponse.from("success"));
    }

    @PostMapping("refresh")
    public ResponseEntity<TokenRefreshResponse> refresh(
            @RequestBody @Valid TokenRefreshRequest request
    ) {
        TokenRefreshResponse newTokens = userService.refresh(request);
        return ResponseEntity.ok(newTokens);
    }


    @PostMapping("sms")
    public ResponseEntity<SuccessResponse> sms(@RequestBody @Valid SmsRequest request) throws Exception {
        this.smsService.sendVerifyCode(request.getPhone(), request.getApplication());
        return ResponseEntity.ok(SuccessResponse.from("success"));
    }

    @PostMapping("sms/verification")
    public ResponseEntity<SuccessResponse> verifySms(@RequestBody @Valid SmsVerificationRequest request) {
        this.smsService.verifyCode(request.getPhone(), request.getCode());
        return ResponseEntity.ok(SuccessResponse.from("success"));
    }
}
