package com.chenminhua.enabledemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEcho(packages = {"com.chenminhua.enabledemo.dto", "com.chenminhua.enabledemo.vo"})
@SpringBootApplication
public class DemoApp {
    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class);
    }
}
