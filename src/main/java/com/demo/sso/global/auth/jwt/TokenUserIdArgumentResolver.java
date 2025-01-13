package com.demo.sso.global.auth.jwt;

import com.demo.sso.global.auth.exception.UnAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class TokenUserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @TokenUserId 어노테이션이 있는지 확인
        return parameter.getParameterAnnotation(UserId.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        // Authorization 헤더에서 JWT 토큰 추출
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw UnAuthenticationException.withDetail("토큰이 없습니다");
        }

        String token = authorizationHeader.replace("Bearer ", "");


        // JWT 토큰에서 userId 추출
        return Long.parseLong(jwtProvider.getSubject(token));
    }
}
