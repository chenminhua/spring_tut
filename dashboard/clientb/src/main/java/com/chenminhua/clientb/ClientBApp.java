package com.chenminhua.clientb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ClientBApp {

    @GetMapping("/")
    public String name () {
        return "b";
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientBApp.class);
    }
}
