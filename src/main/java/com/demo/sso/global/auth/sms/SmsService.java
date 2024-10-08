package com.demo.sso.global.auth.sms;

import com.demo.sso.global.infra.naver.NaverApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final NaverApiClient naverApiClient;


    public void sendVerifyCode(String phone) throws Exception {
        String verifyCode = generateVerifyCode();
        naverApiClient.requestSMS(phone, String.format("[HeartOn] 인증번호는 %s 입니다", verifyCode));

    }

    private String generateVerifyCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
