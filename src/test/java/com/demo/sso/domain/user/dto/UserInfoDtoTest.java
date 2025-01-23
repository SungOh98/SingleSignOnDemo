package com.demo.sso.domain.user.dto;


import com.demo.sso.domain.user.domain.Gender;
import com.demo.sso.global.infra.kakao.KakaoInfoResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoDtoTest {
    @Test
    public void 카카오_유저정보_테스트() throws Exception{
        //given
        // Profile과 KakaoAccount는 반드시 얻어온다는 가정!
        KakaoInfoResponse.Profile profile = new KakaoInfoResponse.Profile();
        KakaoInfoResponse.KakaoAccount kakaoAccount = new KakaoInfoResponse.KakaoAccount();
        kakaoAccount.setProfile(profile);
        kakaoAccount.setBirthyear("1998");
        kakaoAccount.setBirthday("0209");
        kakaoAccount.setGender("male");
        KakaoInfoResponse kakaoInfoResponse = new KakaoInfoResponse();
        kakaoInfoResponse.setKakaoAccount(kakaoAccount);
        //when
        UserInfoDto userInfoDto = new UserInfoDto(kakaoInfoResponse);
        //then
        LocalDate dateRes = LocalDate.of(1998, 2, 9);
        Gender genRes = Gender.남;
        assertEquals(dateRes, userInfoDto.getBirthyear());
        assertEquals(genRes, userInfoDto.getGender());
     }

     @Test
     public void 열거형_Test() throws Exception{
         //given
         Gender gender = Gender.남;

         System.out.print(gender.name());
         System.out.print(gender.toString());
         //when

         //then

      }

}