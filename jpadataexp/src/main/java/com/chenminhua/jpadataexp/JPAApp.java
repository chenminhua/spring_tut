package com.chenminhua.jpadataexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JPAApp implements CommandLineRunner {

    @Autowired private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(JPAApp.class);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("chenminhua", "fewfwe", 24));
        User user = userRepository.findByUserName("chenminhua");
        System.out.println(user);
    }
}
