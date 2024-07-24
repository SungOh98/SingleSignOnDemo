package com.demo.sso.global.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "livinAI SSO Server API 명세서",
                description = "API Docs for LivinAI SSO Server.",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {
}
