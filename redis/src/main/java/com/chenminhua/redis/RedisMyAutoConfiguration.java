package com.chenminhua.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;




//@Configuration
public class RedisMyAutoConfiguration {

    public static class Cat {}

    @Bean
    public RedisTemplate<String, Cat> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Cat> template = new RedisTemplate<>();

        RedisSerializer<Cat> values = new Jackson2JsonRedisSerializer<Cat>(Cat.class);
        RedisSerializer keys = new StringRedisSerializer();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(keys);
        template.setValueSerializer(values);
        template.setHashKeySerializer(keys);
        template.setHashValueSerializer(values);
        return template;
    }

}
