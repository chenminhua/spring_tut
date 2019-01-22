package com.chenminhua.getallbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Override
    public void run(String... args) throws Exception {

        String[] beans = applicationContext.getBeanDefinitionNames();
        for (String bean : beans) {
            System.out.println(bean);
        }

    }


}
