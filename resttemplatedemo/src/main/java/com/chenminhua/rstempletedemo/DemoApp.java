package com.chenminhua.rstempletedemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * https://www.baeldung.com/rest-template
 */

@SpringBootApplication
public class DemoApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String fooResourceUrl
                = "http://localhost:8080/spring-rest/foos";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
    }
}
