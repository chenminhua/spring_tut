package com.chenminhua.rabbitmqdemo;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@EnableRabbit
@SpringBootApplication
public class DemoApp extends SpringBootServletInitializer implements RabbitListenerConfigurer {

    @Bean
    public ApplicationConfigReader applicationConfig() {
        return new ApplicationConfigReader();
    }

    @Autowired private ApplicationConfigReader applicationConfig;

    public ApplicationConfigReader getApplicationConfig() {
        return applicationConfig;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApp.class);
    }

    @Bean
    public TopicExchange getApp1Exchange() {
        return new TopicExchange(getApplicationConfig().getApp1Exchange());
    }

    @Bean
    public Queue getApp1Queue() {
        return new Queue(getApplicationConfig().getApp1Queue());
    }

    @Bean
    public Binding declareBindingApp1() {
        return BindingBuilder.bind(getApp1Queue())
                .to(getApp1Exchange())
                .with(getApplicationConfig().getApp1RoutingKey());
    }

    /* Creating a bean for the Message queue Exchange */
    @Bean
    public TopicExchange getApp2Exchange() {
        return new TopicExchange(getApplicationConfig().getApp2Exchange());
    }
    /* Creating a bean for the Message queue */
    @Bean
    public Queue getApp2Queue() {
        return new Queue(getApplicationConfig().getApp2Queue());
    }
    /* Binding between Exchange and Queue using routing key */
    @Bean
    public Binding declareBindingApp2() {
        return BindingBuilder.bind(getApp2Queue())
                .to(getApp2Exchange())
                .with(getApplicationConfig().getApp2RoutingKey());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class);
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}

@Data
@Configuration
@PropertySource("classpath:application.properties")
class ApplicationConfigReader {
    @Value("${app1.exchange.name}")
    private String app1Exchange;

    @Value("${app1.queue.name}")
    private String app1Queue;

    @Value("${app1.routing.key}")
    private String app1RoutingKey;

    @Value("${app2.exchange.name}")
    private String app2Exchange;

    @Value("${app2.queue.name}")
    private String app2Queue;

    @Value("${app2.routing.key}")
    private String app2RoutingKey;
}
