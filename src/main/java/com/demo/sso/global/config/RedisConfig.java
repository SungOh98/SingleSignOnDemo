package com.demo.sso.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    // String : Java 객체 형태로 저장하기 위한 템플릿. -> Refresh Token, 문자 인증에서 사용
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        // Key는 String으로 설정
        template.setKeySerializer(new StringRedisSerializer());
        // Value는 Java 객체를 Json으로 직렬화하여 저장.
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Hash를 사용할 경우 시리얼라이저 (String : Set<Java 객체>)
        template.setHashKeySerializer(new StringRedisSerializer());
        // Value는 Json 형태로 저장.
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    // String : String 형태로 저장하기 위한 템플릿. -> 문자 인증에서 사용
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
