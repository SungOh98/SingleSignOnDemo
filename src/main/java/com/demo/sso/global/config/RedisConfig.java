package com.demo.sso.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
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

    /**
     * Hash 자료구조를 사용하기 위한 템플릿
     * Java 객체를 Redis Hash 자료구조 형태로 저장한다.
     * {key1 : {field1 : value1, field2 : value2, ...}, ...}
     * {
     *      "sms:1" : {code : "1번유저의 인증코드값", requestCount : "1번유저가 요청한 수"},
     *      "sms:2" : {code : "2번유저의 인증코드값", requestCount : "2번유저가 요청한 수"},
     *      ...
     * }
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        // Key는 String으로 설정
        template.setKeySerializer(new StringRedisSerializer());
        // Value는 Java 객체를 Json으로 직렬화하여 저장.
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

//        // field는 문자열로 설정 => field가 hashKey다.
//        template.setHashKeySerializer(new StringRedisSerializer());
//        // Value는 Json 형태로 저장.
//        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    /**
     * String 자료구조를 사용하기 위한 템플릿
     {key1 : value1, key2 : value2}
     StringRedisTemplate는 RedisTemplate<String, String>임.
     {
        010-1234-5678 : blacklist,
        010-3333-4444 : blacklist,
     ...
     }
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }


}
