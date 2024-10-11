package com.demo.sso.global.auth.sms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;


@Getter
@NoArgsConstructor
@RedisHash("sms")
public class VerifyCode {
    private static final long EXPIRATION_MINUTES = 3;

    @Id
    private String phone;
    private String code;
    private int requestCount;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private long expireMinutes;


    public void incrementRequestCount() {
        requestCount++;
    }


    private VerifyCode(String phone, String code, int requestCount, long expireMinutes) {
        this.phone = phone;
        this.code = code;
        this.requestCount = requestCount;
        this.expireMinutes = expireMinutes;
    }

    public static VerifyCode create(String phone, String code, int requestCount) {
        return new VerifyCode(phone, code, requestCount, EXPIRATION_MINUTES);
    }
}
