package com.demo.sso.global.infra.naver.sms;

import com.demo.sso.global.infra.naver.exception.NaverSMSFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;



@Component
@RequiredArgsConstructor
public class NaverApiClient {
    @Value("${sms.service-id}")
    private String serviceId;

    @Value("${sms.base-url}")
    private String baseUrl;

    @Value("${sms.access-key}")
    private String accessKey;

    @Value("${sms.secret-key}")
    private String secretKey;

    @Value("${sms.phone-number}")
    private String sender;

    private final RestTemplate restTemplate;

    public void requestSMS(String receiver, String content) throws Exception {
        // URL 생성
        String uri = "/sms/v2/services/" + serviceId + "/messages";
        String timestamp = Long.toString(System.currentTimeMillis());
        String naverSignature = makeSignature(timestamp, uri);
        // Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", timestamp);
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", naverSignature);


        // body 생성
        MessageDto messageDto = new MessageDto(receiver);
        List<MessageDto> messages = new ArrayList<>();
        messages.add(messageDto);
        NaverSmsRequestDto body = NaverSmsRequestDto.builder()
                .type("SMS")
                .from(sender)
                .content(content)
                .messages(messages)
                .build();

        // body와 헤더 조립
        HttpEntity<NaverSmsRequestDto> requestData = new HttpEntity<>(body, headers);

        // 요청
        ResponseEntity<NaverSmsResponseDto> response = restTemplate.exchange(
                baseUrl + uri,
                HttpMethod.POST,
                requestData,
                NaverSmsResponseDto.class
        );

        NaverSmsResponseDto responseBody = response.getBody();
        if (responseBody == null || !"202".equals(responseBody.getStatusCode())) {
            throw NaverSMSFailedException.withDetail("문자 호출과정에서 문제가 발생했습니다.");
        }

    }

    public String makeSignature(String timestamp, String uri) throws Exception {
        String method = "POST";
        // Python과 동일한 형식의 메시지 생성
        String message = method + " " + uri + "\n" + timestamp + "\n" + accessKey;
        // SecretKeySpec 객체 생성
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        // Mac 인스턴스 생성
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        // HMAC-SHA256으로 암호화
        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        // Base64로 인코딩
        return Base64.getEncoder().encodeToString(rawHmac);
    }

}

