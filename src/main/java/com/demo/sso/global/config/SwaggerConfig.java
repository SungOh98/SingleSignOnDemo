package com.demo.sso.global.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
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
//        @Bean
//        public OpenAPI openAPI() {
//                String jwt = "Jwt Authentication";
//                SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
//                Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
//                        .name(jwt)
//                        .type(SecurityScheme.Type.HTTP)
//                        .scheme("Bearer")
//                        .description("토큰값을 입력하여 인증을 활성화할 수 있습니다.")
//                        .bearerFormat("JWT")
//                );
//                return new OpenAPI()
//                        .components(new Components())
//                        .addSecurityItem(securityRequirement)
//                        .components(components);
//
//        }
}
