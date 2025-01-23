package com.demo.sso.global.infra.kakao;

import com.demo.sso.global.config.KakaoOAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class KakaoApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    private final KakaoOAuthProperties kakaoOAuthProperties;

    private final RestTemplate restTemplate;


    public String requestAccessToken(KakaoLoginParams params) {
        String uri = kakaoOAuthProperties.getAuthUrl() + "/oauth/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", kakaoOAuthProperties.getClientIdMap().get(params.getApplication()));
        body.add("redirect_uri", params.getRedirectUri());
        body.add("code", params.getAuthorizationCode());

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        KakaoToken response = restTemplate.postForObject(uri, request, KakaoToken.class);

        assert response != null;
        return response.getAccessToken();
    }


    public KakaoInfoResponse requestUserInfo(String accessToken) {
        String url = kakaoOAuthProperties.getApiUrl() + "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        // 모든 정보를 다 받는 GET 요청 보내기
        return restTemplate.exchange(url, HttpMethod.GET, request, KakaoInfoResponse.class).getBody();
    }

}
