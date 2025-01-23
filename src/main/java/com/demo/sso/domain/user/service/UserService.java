package com.demo.sso.domain.user.service;

import com.demo.sso.domain.user.domain.User;
import com.demo.sso.domain.user.dto.*;
import com.demo.sso.global.auth.jwt.AuthTokens;
import com.demo.sso.global.infra.kakao.KakaoLoginParams;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    AuthTokens login(LoginRequest request);

    void logout(Long user_id);

    TokenRefreshResponse refresh(TokenRefreshRequest request);

    Long totalSignUp(SignUpRequest request);

    KakaoLoginResponse kakaoLoginV1(KakaoLoginParams params);

    KakaoLoginResponse kakaoLoginV2(KakaoLoginParams params);

    KakaoLoginResponse kakaoLoginByApp(KakaoTokenRequest request);

    UserResponse findUser(Long userId);

    UserResponse updateUser(Long userId, UpdateUserRequest request);

    void deleteUser(Long userId);

    List<User> findUsersById(SimpleUserRequest request);

    List<User> findUsersByName(SearchUserRequest request);
}

