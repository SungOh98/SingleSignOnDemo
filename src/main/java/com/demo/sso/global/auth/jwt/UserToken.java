package com.demo.sso.global.auth.jwt;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@RedisHash("refresh_token")
public class UserToken {
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 5;
    // Key : refresh_token:{Id}
    // Value : UserToken 객체
    @Id
    private Long userId;
    private final String refreshToken;
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private final Long expiration;

    private UserToken(Long userId, String refreshToken, Long expiration) {
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }

    public static UserToken create(Long userId, String refreshToken) {
        return new UserToken(userId, refreshToken, REFRESH_TOKEN_EXPIRE_TIME);
    }
}
