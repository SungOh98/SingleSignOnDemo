package com.demo.sso.domain.user.api;

import com.demo.sso.domain.user.dto.*;
import com.demo.sso.domain.user.service.UserService;
import com.demo.sso.global.auth.jwt.AuthTokens;
import com.demo.sso.global.auth.sms.SmsService;
import com.demo.sso.global.exception.SuccessResponse;
import com.demo.sso.global.infra.kakao.KakaoLoginParams;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class UserController implements UserApi {
    private final UserService userService;
    private final SmsService smsService;

    @PostMapping("login/kakao")
    public ResponseEntity<KakaoLoginResponse> kakaoLogin(@RequestBody KakaoLoginParams params) {
        KakaoLoginResponse kakaoLoginResponse = userService.kakaoLogin(params);
        return ResponseEntity.ok(kakaoLoginResponse);
    }


    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthTokens tokens = userService.login(request);
        return ResponseEntity.ok(new LoginResponse(tokens));
    }

    @PostMapping("logout")
    public ResponseEntity<SuccessResponse> logout(@RequestHeader("Authorization") String accessToken) {
        if (accessToken.startsWith("Bearer ")) accessToken = accessToken.replace("Bearer ", "");
        userService.logout(accessToken);
        return ResponseEntity.ok(SuccessResponse.from("success"));
    }

    @PostMapping("refresh")
    public ResponseEntity<TokenRefreshResponse> refresh(
            @RequestBody @Valid TokenRefreshRequest request
    ) {
        TokenRefreshResponse newTokens = userService.refresh(request);
        return ResponseEntity.ok(newTokens);
    }

    /**
     * 인증 테스트 API
     */

    @GetMapping("users")
    public ResponseEntity<UsersResponse> users(@RequestHeader("Authorization") String accessToken) {
        if (accessToken.startsWith("Bearer ")) accessToken = accessToken.replace("Bearer ", "");
        log.info("token : {}", accessToken);
        List<UserInfoResponse> collect = userService.users(accessToken).stream()
                .map(UserInfoResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new UsersResponse(collect));
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
