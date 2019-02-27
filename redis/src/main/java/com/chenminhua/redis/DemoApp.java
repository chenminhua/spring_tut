package com.chenminhua.redis;

import lombok.extern.java.Log;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@Log
@SpringBootApplication
public class DemoApp {
//    public static class Cat {}
//
//    @Bean
//    public RedisTemplate<String, Cat> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Cat> template = new RedisTemplate<>();
//
//        RedisSerializer<Cat> values = new Jackson2JsonRedisSerializer<Cat>(Cat.class);
//        RedisSerializer keys = new StringRedisSerializer();
//        template.setConnectionFactory(connectionFactory);
//        template.setKeySerializer(keys);
//        template.setValueSerializer(values);
//        template.setHashKeySerializer(keys);
//        template.setHashValueSerializer(values);
//        return template;
//    }

    private ApplicationRunner titleRunner (String title, ApplicationRunner rr) {
        return args -> {
            log.info(title.toUpperCase() + ":");
            rr.run(args);
        };
    }

    @Bean
    ApplicationRunner geography (RedisTemplate<String, String> rt) {
        return titleRunner("geography", args -> {
            GeoOperations<String, String> geo = rt.opsForGeo();
            geo.add("Sicily", new Point(13.361389, 38.1155556), "Arigento");
        })
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class);
    }
}
