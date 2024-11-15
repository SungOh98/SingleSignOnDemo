package com.demo.sso.global.infra.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${oauth.url.auth}")
    private String authUrl;

    @Value("${oauth.url.api}")
    private String apiUrl;

//    @Value("${oauth.url.redirect}")
//    private String redirectUri;

    @Value("${oauth.client-id}")
    private String clientId;

    private final RestTemplate restTemplate;


    public String requestAccessToken(KakaoLoginParams params) {
        String uri = authUrl + "/oauth/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("redirect_uri", params.getRedirectUri());
        body.add("code", params.getAuthorizationCode());

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        KakaoToken response = restTemplate.postForObject(uri, request, KakaoToken.class);

        assert response != null;
        return response.getAccessToken();
    }


    public KakaoInfoResponse requestUserInfo(String accessToken) {
        String url = apiUrl + "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

//        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);


//        return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
        // 모든 정보를 다 받는 GET 요청 보내기
        return restTemplate.exchange(url, HttpMethod.GET, request, KakaoInfoResponse.class).getBody();
    }

}
