package com.demo.sso.domain.user.dao;

import com.demo.sso.global.auth.jwt.UserToken;

import java.util.Optional;

public interface UserTokenRepository {
    // Token 저장.
    UserToken save(UserToken userToken);

    // Token 조회.
    Optional<UserToken> findById(Long userId);

    // Token 삭제
    void deleteById(Long userId);

    void addTokenToBlackList(Long userId, String token);
}
