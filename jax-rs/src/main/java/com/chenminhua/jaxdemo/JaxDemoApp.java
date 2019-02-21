package com.chenminhua.jaxdemo;


import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.stream.Stream;


@SpringBootApplication
public class JaxDemoApp {

    @Bean
    ApplicationRunner initData(ConsumerRepository cr) {
        return args -> Stream.of("A", "B", "C").forEach(x -> cr.save(new Consumer(null, x)));
    }


    public static void main(String[] args) {
        SpringApplication.run(JaxDemoApp.class);
    }
}

@Component
class GenericExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException e) {
        return Response.serverError().entity(e.getMessage()).build();
    }
}


@Configuration
class JerseyConfiguration {

    @Bean
    ConsumerResource consumerResource(ConsumerRepository repository) {
        return new ConsumerResource(repository);
    }

    @Bean
    ResourceConfig config(ConsumerResource cr, GenericExceptionMapper exceptionMapper) {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(exceptionMapper);
        resourceConfig.register(cr);
        return resourceConfig;
    }
}


