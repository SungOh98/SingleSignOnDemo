package com.demo.sso.global.auth.sms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VerifyCodeRepositoryTest {

    @Autowired
    VerifyCodeRepository verifyCodeRepository;
    @Test
    @Rollback(false)
    public void 인증_토큰_저장() throws Exception{
        //given

        String phone = "010-1234-5678";
        String code = "123456";
        verifyCodeRepository.save(VerifyCode.create(phone, code));
        //when
//        VerifyCode byPhone = verifyCodeRepository.findByPhone(phone);
//        Assertions.assertEquals(code, byPhone.getCode());
//        verifyCodeRepository.deleteByPhone(phone);
//        assertFalse(verifyCodeRepository.isExist(phone));

        //then

     }

}