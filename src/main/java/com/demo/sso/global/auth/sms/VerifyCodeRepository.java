package com.demo.sso.global.auth.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

/**
 * 사용제한
 * 1. 같은 핸드폰으로 여러번 요청하지 못하게 -> 내가 처리
 * 2. 같은 기기에서 번호를 바꿔가며 여러번 요청하지 못하게 -> Client에서 처리
 *
 */
@Repository
@RequiredArgsConstructor
public class VerifyCodeRepository{
    private final String PREFIX = "sms:";
    private final int LIVE_SEC = 60 * 3; // 3분
    private final StringRedisTemplate redisTemplate;


    public void save(String phone, String code) {
        redisTemplate.opsForValue().set(
                PREFIX + phone,
                code,
                Duration.ofSeconds(LIVE_SEC)
        );
    }

    public String findByPhone(String phone) {
        return redisTemplate.opsForValue().get(PREFIX + phone);
    }


    public void delete(String phone) {
        redisTemplate.delete(PREFIX + phone);
    }
}
