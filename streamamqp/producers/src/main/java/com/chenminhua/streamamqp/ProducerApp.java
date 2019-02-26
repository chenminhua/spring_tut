package com.chenminhua.streamamqp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableBinding(ProducerChannels.class)
public class ProducerApp {

    private final MessageChannel consumer;

    public ProducerApp(ProducerChannels channels) {
        this.consumer = channels.consumer();
    }

    @PostMapping("/greet/{name}")
    public void publish(@PathVariable String name) {
        System.out.println("---------" + name + "----------------");
        String greeting = "Hello, " + name + "!";
        Message<String> msg = MessageBuilder.withPayload(greeting).build();
        this.consumer.send(msg);

    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApp.class);
    }
}


interface ProducerChannels {
    @Output
    MessageChannel consumer();

}