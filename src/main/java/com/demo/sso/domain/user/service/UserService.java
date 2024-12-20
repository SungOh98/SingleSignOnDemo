package com.demo.sso.domain.user.service;

import com.demo.sso.domain.user.domain.User;
import com.demo.sso.domain.user.dto.*;
import com.demo.sso.global.auth.jwt.AuthTokens;
import com.demo.sso.global.infra.kakao.KakaoLoginParams;

import java.util.List;

public interface UserService {
    AuthTokens login(LoginRequest request);
    void logout(String token);
    TokenRefreshResponse refresh(TokenRefreshRequest request);
    // 테스트
    List<User> users(String accessToken);

    KakaoLoginResponse kakaoLogin(KakaoLoginParams params);

    Long totalSignUp(SignUpRequest request);
}

