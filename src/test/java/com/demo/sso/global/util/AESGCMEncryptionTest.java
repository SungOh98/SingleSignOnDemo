package com.demo.sso.global.util;

import com.demo.sso.global.util.encrytion.AESGCMEncryption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AESGCMEncryptionTest {
    @Autowired
    private AESGCMEncryption aesGCMEncryption;


    @Test
    public void 암호화_테스트() throws Exception{
        //given
        String plainText = "평문";

        //when
        String encrypt = aesGCMEncryption.encrypt(plainText);
        System.out.println(encrypt);

        //then

        Assertions.assertEquals(plainText, aesGCMEncryption.decrypt(encrypt));

     }
}