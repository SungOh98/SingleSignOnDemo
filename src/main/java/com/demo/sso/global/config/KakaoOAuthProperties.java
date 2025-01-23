package com.demo.sso.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "oauth.kakao")
@Getter @Setter
public class KakaoOAuthProperties {
    private String authUrl;
    private String apiUrl;
    private Map<String, String> clientIdMap;
}
