package com.chenminhua.redis;

import lombok.extern.java.Log;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.time.Instant;

@Log
@Configuration
public class PubSubDemoConfig {

    private final String topic = "chat";

    @Bean
    ApplicationRunner pubsub (RedisTemplate<String, String> rt) {
        return titleRunner("pub/sub", args -> {
            rt.convertAndSend(topic, "hello world @ " + Instant.now().toString());
        });
    }

    @Bean
    RedisMessageListenerContainer listener (RedisConnectionFactory cf) {
        MessageListener ml = new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                String str = new String(message.getBody());
                log.info("message from " + topic + ": " + str);
            }
        };

        RedisMessageListenerContainer mlc = new RedisMessageListenerContainer();
        mlc.setConnectionFactory(cf);
        mlc.addMessageListener(ml, new PatternTopic(this.topic));
        return mlc;
    }

    private ApplicationRunner titleRunner (String title, ApplicationRunner rr) {
        return args -> {
            log.info(title.toUpperCase() + ":");
            rr.run(args);
        };
    }
}
