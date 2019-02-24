package com.chenminhua.zipkinservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZKPServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(ZKPServiceApp.class);
    }

}
