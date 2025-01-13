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
    private final SmsBlackListRepository smsBlackListRepository;
    private final VerifyCodeRepository verifyCodeRepository;
    private final int MAX_SEND_COUNT = 3;


    private void validateInBlackList(String phone) {
        // 블랙 리스트에 추가된 폰이라면
        if (smsBlackListRepository.isBlack(phone)) {
            // 있던 인증 코드 삭제
            verifyCodeRepository.deleteByPhone(phone);
            // 429 예외 터트리기
            throw TooManyRequestException.withDetail(phone);
        }
    }
    // 새롭게 블랙리스트에 추가될 대상이라면 블랙리스트에 추가하고 예외터트리기
    private void validateBlack(VerifyCode verifyCode) {
        if (verifyCode.getRequestCount() > MAX_SEND_COUNT) {
            // 있던 인증 코드 삭제
            verifyCodeRepository.deleteByPhone(verifyCode.getPhone());
            // 블랙 리스트 추가
            smsBlackListRepository.addBlackList(verifyCode.getPhone());
            // 429 예외 터트리기
            throw TooManyRequestException.withDetail(verifyCode.getCode());
        }

    }


    // 인증번호 전송
    public void sendVerifyCode(String phone, String application) throws Exception {
        // 블랙리스트에 있는지 검사
        validateInBlackList(phone);
        // 랜덤코드 생성
        String randomCode = generateVerifyCode();

        int requestCount = 0;
        if (verifyCodeRepository.isExist(phone)) {
            requestCount = verifyCodeRepository.findByPhone(phone).getRequestCount();
        }

        VerifyCode verifyCode = VerifyCode.create(phone, randomCode, requestCount);
        // 요청횟수 증가
        verifyCode.incrementRequestCount();
        // 신규 블랙리스트 대상인지 검사
        validateBlack(verifyCode);
        // 문자메시지 발송
        naverApiClient.requestSMS(
                phone.replace("-", ""),
                String.format("[%s] 인증번호는 %s 입니다.", application, randomCode)
        );
        // Redis에 저장
        verifyCodeRepository.save(verifyCode);

    }
    // 인증번호 검사
    public void verifyCode(String phone, String code) {
        String storedCode = verifyCodeRepository.findByPhone(phone).getCode();
        // 시간이 만료될 경우
        if (storedCode == null)
            throw ExpiredCodeException.withDetail(String.format("폰 번호 : %s, 인증번호 : %s", phone, code));
        // 인증번호가 틀릴 경우
        if (!storedCode.equals(code)) {
            throw UnValidCodeException.withDetail(String.format("폰 번호 : %s, 인증번호 : %s", phone, code));
        }
        // 인증 성공한 경우 -> 저장되어 있는 토큰 삭제.
        verifyCodeRepository.deleteByPhone(phone); // 저장한 인증번호 삭제
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
