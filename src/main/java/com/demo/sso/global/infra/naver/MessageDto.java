package com.demo.sso.global.infra.naver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageDto {
    // Mandatory
    private String to;
    // Optional
}