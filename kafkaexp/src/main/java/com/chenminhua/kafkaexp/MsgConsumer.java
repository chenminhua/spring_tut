package com.chenminhua.kafkaexp;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MsgConsumer {
    @KafkaListener(topics = {"topic-1", "topic-2"})
    public void processMsg(String content) {
        System.out.println("消费消息" + content);
    }
}
