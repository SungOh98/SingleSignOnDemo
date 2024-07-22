package com.demo.sso.domain.user.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Client {
    @GetMapping("/kakao/auth-code")
    public String showAuthCode(@RequestParam String code) {
        return code;
    }
}
