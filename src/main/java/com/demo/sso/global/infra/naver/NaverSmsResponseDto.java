package com.demo.sso.global.infra.naver;


import lombok.Data;

@Data
public class NaverSmsResponseDto {
    private String requestId;
    private String requestTime;
    private String statusCode;
    private String statusName;
}
