package com.demo.sso.global.infra.naver;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaverApiClientTest {

    @Autowired
    NaverApiClient naverApiClient;

    @Test
    public void 문자전송_테스트() throws Exception{
        //given
        naverApiClient.requestSMS("01075587248", "안녕?");
        //when

        //then

     }

}