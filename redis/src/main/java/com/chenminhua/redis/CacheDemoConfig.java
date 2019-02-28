package com.chenminhua.redis;

import lombok.extern.java.Log;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Log
@EnableCaching
@Configuration
public class CacheDemoConfig {

    private ApplicationRunner titleRunner (String title, ApplicationRunner rr) {
        return args -> {
            log.info(title.toUpperCase() + ":");
            rr.run(args);
        };
    }

    @Bean
    CacheManager redisCache(RedisConnectionFactory cf) {
        return RedisCacheManager.builder(cf).build();
    }

    private long measure(Runnable r) {
        long start = System.currentTimeMillis();
        r.run();
        long stop = System.currentTimeMillis();
        return stop - start;
    }

    @Bean
    ApplicationRunner cache(OrderService orderService) {
        return titleRunner("caching", a -> {
            Runnable measure = () -> orderService.byId(1L);
            log.info("first " + measure(measure));
            log.info("second " + measure(measure));
            log.info("third " + measure(measure));
        });
    }

}



@Log
@Service
class OrderService {
    @Cacheable("order-by-id")
    public Order byId(Long id) {
        try {
            Thread.sleep(1000 * 10);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return new Order(id, new Date(), Collections.emptyList());
    }
}

