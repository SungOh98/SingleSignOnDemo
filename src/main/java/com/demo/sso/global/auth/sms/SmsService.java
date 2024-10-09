package com.demo.sso.global.auth.sms;

import com.demo.sso.global.auth.exception.ExpiredCodeException;
import com.demo.sso.global.auth.exception.TooManyRequestException;
import com.demo.sso.global.auth.exception.UnValidCodeException;
import com.demo.sso.global.infra.naver.NaverApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final NaverApiClient naverApiClient;
    private final RequestLimitRepository requestLimitRepository;
    private final VerifyCodeRepository verifyCodeRepository;
    private final int MAX_SEND_COUNT = 3;


    // 인증번호 전송
    public void sendVerifyCode(String phone) throws Exception {
        // 전송 횟수가 최대 전송 가능 횟수에 도달했을 때는 예외 터트림
        if (!isLessThanMaxSend(phone)) {
            // 인증 코드 삭제
            verifyCodeRepository.delete(phone);
            // 예외 터트리기
            throw TooManyRequestException.withDetail(phone);
        }
        // 전송 코드 생성
        String verifyCode = generateVerifyCode();
        // 문자 메시지 전송
        naverApiClient.requestSMS(phone.replace("-", ""), String.format("[HeartOn] 인증번호는 %s 입니다", verifyCode));
        // 전송 횟수 추가
        incrementCount(phone);
        // 전송 코드 저장
        verifyCodeRepository.save(phone, verifyCode);

    }
    // 인증번호 검사
    public void verifyCode(String phone, String code) {
        String storedCode = verifyCodeRepository.findByPhone(phone);
        // 시간이 만료될 경우
        if (storedCode == null)
            throw ExpiredCodeException.withDetail(String.format("폰 번호 : %s, 인증번호 : %s", phone, code));
        // 인증번호가 틀릴 경우
        if (!storedCode.equals(code)) {
            throw UnValidCodeException.withDetail(String.format("폰 번호 : %s, 인증번호 : %s", phone, code));
        }

        // 인증 성공한 경우 -> 저장되어 있는 토큰 삭제.
        verifyCodeRepository.delete(phone); // 저장한 인증번호 삭제
        requestLimitRepository.delete(phone); // 유효 횟수 제한 삭제
    }

    // 유효 전송 횟수가 3미만인지 검사
    private boolean isLessThanMaxSend(String phone) {
        String value = requestLimitRepository.findByPhone(phone);
        int cnt = 0;
        if (value != null) cnt = Integer.parseInt(value);

        return cnt < MAX_SEND_COUNT;
    }

    // 전송 횟수 추가
    private void incrementCount(String phone) {
        int nextCnt = 0;
        String value = requestLimitRepository.findByPhone(phone);
        if (value != null) nextCnt = Integer.parseInt(value);

        requestLimitRepository.save(phone, ++nextCnt);

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
