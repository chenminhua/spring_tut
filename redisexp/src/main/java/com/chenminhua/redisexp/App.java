package com.chenminhua.redisexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired private RedisTemplate<String, User> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }


    @Override
    public void run(String... args) throws Exception {
        String random = (String) redisTemplate.randomKey();
        User user = new User("cmh", 28);
        redisTemplate.opsForValue().set(user.getName(), user);
    }
}
