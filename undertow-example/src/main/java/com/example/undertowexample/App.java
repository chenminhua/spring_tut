package com.example.undertowexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    // 以前需要手动绑定，现在已经不需要了
//    @Bean
//    public UndertowServletWebServerFactory embeddedServletContainerFactory() {
//        UndertowServletWebServerFactory factory =
//                new UndertowServletWebServerFactory();
//
//        factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
//            @Override
//            public void customize(io.undertow.Undertow.Builder builder) {
//                builder.addHttpListener(8080, "0.0.0.0");
//            }
//        });
//
//        return factory;
//    }

}
