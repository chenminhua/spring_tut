package com.chenminhua.kafkaexp;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Configuration
@EnableScheduling
public class ProduceMsgTask{

    @Autowired private MsgProducer msgProducer;

    @Scheduled(fixedRate = 3000)
    public void sendMessage(){
        System.out.println("send message");
        msgProducer.sendMessage("topic-1", "fewfwe");
    }
}
