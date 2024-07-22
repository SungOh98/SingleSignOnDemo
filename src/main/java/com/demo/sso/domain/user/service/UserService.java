package com.demo.sso.domain.user.service;

import com.demo.sso.domain.user.domain.User;
import com.demo.sso.domain.user.dto.LoginRequest;
import com.demo.sso.domain.user.dto.SignUpRequest;
import com.demo.sso.domain.user.dto.TokenRefreshRequest;
import com.demo.sso.domain.user.dto.TokenRefreshResponse;
import com.demo.sso.global.auth.jwt.AuthTokens;

import java.util.List;

public interface UserService {
    Long signUp(SignUpRequest request);
    AuthTokens loginByOauth();
    AuthTokens login(LoginRequest request);
    void logout(String token);
    TokenRefreshResponse refresh(TokenRefreshRequest request);
    // 테스트
    List<User> users(String accessToken);
}
