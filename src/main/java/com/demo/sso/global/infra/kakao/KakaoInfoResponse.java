package com.demo.sso.global.infra.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 파싱할 때 클래스 속성이 아닌 값은 무시
public class KakaoInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {

        private Profile profile;
        private String email;
        private String name;
        private String birthyear;
        private String birthday;
        private String gender;
        @JsonProperty("phone_number")
        private String phoneNumber;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Profile {

        private String nickname;
    }

    public String getAccount() {
        return kakaoAccount.getEmail();
    }

    public String getNickName() {
        return kakaoAccount.getProfile().getNickname();
    }

    public String getBirthyear() {
        return kakaoAccount.getBirthyear();
    }

    public String getBirthday() {
        return kakaoAccount.getBirthday();
    }


    public String getGender() {
        return kakaoAccount.getGender();
    }

    public String getPhoneNumber() {
        return kakaoAccount.getPhoneNumber();
    }

    public String getName() {
        return kakaoAccount.getName();
    }

    @Override
    public String toString() {
        return String.format("""
                kakaoaccount : %s, nickname : %s, name : %s, birthyear : %s, gender : %s, phone : %s
                """, getAccount(), getNickName(), getName(), getBirthyear(), getGender(), getPhoneNumber());
    }


}
