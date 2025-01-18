package com.demo.sso.global.auth.jwt;

import com.demo.sso.domain.user.dao.UserRepository;
import com.demo.sso.domain.user.domain.User;
import com.demo.sso.domain.user.domain.UserType;
import com.demo.sso.global.auth.exception.UnAuthorizationException;
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
public class TokenAdminIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @TokenUserId 어노테이션이 있는지 확인
        return parameter.getParameterAnnotation(AdminOnly.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        // Authorization 헤더에서 JWT 토큰 추출
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw UnAuthorizationException.withDetail("토큰이 없습니다");
        }

        String token = authorizationHeader.replace("Bearer ", "");

        Long userId = Long.parseLong(jwtProvider.getSubject(token));
        User user = userRepository.findOne(userId);

        if (!user.getUserType().equals(UserType.admin)) throw UnAuthorizationException.withDetail("관리자만 접근할 수 있습니다.");

        // JWT 토큰에서 userId 추출
        return userId;
    }
}