package com.demo.sso.domain.user.api;

import com.demo.sso.domain.user.dto.*;
import com.demo.sso.domain.user.service.UserService;
import com.demo.sso.global.auth.jwt.AuthTokens;
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
public class UserController implements UserApi{
    private final UserService userService;
    /**
     * kakao로 로그인 API
     * @param params : kakao로 로그인 후 받은 Authentication Code
     * @return
     */
    @RequestMapping("login/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestBody KakaoLoginParams params) {
        return null;
    }

    @PostMapping("sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthTokens tokens = userService.login(request);
        return ResponseEntity.ok(new LoginResponse(tokens));
    }

    @PostMapping("logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accessToken) {
        if (accessToken.startsWith("Bearer ")) accessToken = accessToken.replace("Bearer ", "");
        userService.logout(accessToken);
        return ResponseEntity.ok().build();
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





}
