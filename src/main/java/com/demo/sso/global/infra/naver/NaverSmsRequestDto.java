package com.demo.sso.global.infra.naver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class NaverSmsRequestDto {

    // Mandatory
    private String type;
    private String from;
    private String content;
    private List<MessageDto> messages = new ArrayList<>();

    // Optional


}
