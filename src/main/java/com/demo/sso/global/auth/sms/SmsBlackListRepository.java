package com.demo.sso.global.auth.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;


@Repository
@RequiredArgsConstructor
public class SmsBlackListRepository {
    private final StringRedisTemplate stringRedisTemplate;
    private static final long LIVE_MINUTES = 10;
    private static final String PREFIX = "sms_blacklist:";

    public void addBlackList(String phone) {
        stringRedisTemplate.opsForValue()
                .set(PREFIX + phone, "sms_blackList", LIVE_MINUTES, TimeUnit.MINUTES);
    }

    public boolean isBlack(String phone) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(PREFIX + phone));
    }

}
