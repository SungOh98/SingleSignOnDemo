package com.demo.sso.global.config;

import com.demo.sso.global.auth.jwt.TokenAdminIdArgumentResolver;
import com.demo.sso.global.auth.jwt.TokenUserIdArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenUserIdArgumentResolver tokenUserIdArgumentResolver;
    private final TokenAdminIdArgumentResolver tokenAdminIdArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(tokenUserIdArgumentResolver);
        resolvers.add(tokenAdminIdArgumentResolver);
    }
}