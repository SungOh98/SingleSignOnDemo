package com.demo.sso.global.auth.jwt;

import io.swagger.v3.oas.annotations.Hidden;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 메서드의 파라미터에만 사용
@Retention(RetentionPolicy.RUNTIME) // 런타임에 어노테이션 정보 유지
@Hidden // Swagger 문서에 숨김
public @interface UserOnly {
    // JWT 토큰에서 userID를 추출하기 위한 어노테이션
}
