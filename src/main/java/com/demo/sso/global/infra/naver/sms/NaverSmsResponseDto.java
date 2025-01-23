package com.demo.sso.global.infra.naver.sms;


import lombok.Data;

@Data
public class NaverSmsResponseDto {
    private String requestId;
    private String requestTime;
    private String statusCode;
    private String statusName;
}
