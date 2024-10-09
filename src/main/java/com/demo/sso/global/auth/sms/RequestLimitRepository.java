package com.demo.sso.global.auth.sms;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RequestLimitRepository {
    private final StringRedisTemplate redisTemplate;
    private final String PREFIX = "duration:";
    private final int LIVE_SEC = 60 * 10; // 10분

    public void save(String phone, int cnt) {
        redisTemplate.opsForValue().set(
                PREFIX + phone,
                String.valueOf(cnt),
                Duration.ofSeconds(LIVE_SEC)
        );
    }

    public String findByPhone(String phone) {
        return redisTemplate.opsForValue().get(PREFIX + phone);
    }


    public void delete(String phone) {
        redisTemplate.delete(PREFIX + phone);
    }

    public boolean hasKey(String phone) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + phone));
    }

}
