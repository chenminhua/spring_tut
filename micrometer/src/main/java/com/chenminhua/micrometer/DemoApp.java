package com.chenminhua.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.jmx.JmxMeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@SpringBootApplication
public class DemoApp {

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class);

//        class SlowStatefulThing {
//            public int getCustomersLoggedIntoSystem() {
//                return new Random().nextInt(1000);
//            }
//        }
//
//        SlowStatefulThing customerService = new SlowStatefulThing();
//
//        CompositeMeterRegistry meterRegistry = new CompositeMeterRegistry();
//        meterRegistry.add(new JmxMeterRegistry(null));
//        meterRegistry.add(new PrometheusMeterRegistry(null));
//
//        MeterRegistry mr = meterRegistry;
//        mr.config().commonTags("region", System.getenv("CLOUD_REGION"));
//
//        mr.counter("orders-placed").increment(-123);
//        mr.gauge("speed", 55);
//        mr.gauge("customers-logged-in", customerService, slow -> slow.getCustomersLoggedIntoSystem());
//        mr.timer("transform-photo-job").record(Duration.ofMillis(12));
//        mr.timer("transform-photo-job").record(() -> System.out.println("hello world"));
//        String greeting = mr.timer("transform-photo-job").record(new Supplier<String>() {
//            @Override
//            public String get() {
//                return "hello world";
//            }
//        });
    }

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Bean
    ApplicationRunner runner(MeterRegistry mr) {
        return args -> {
            this.executorService.scheduleAtFixedRate(() -> mr.timer("transform-photo-task").record(Duration.ofMillis((long) (Math.random() * 1000))),
                    500, 500, TimeUnit.MILLISECONDS);
        };
    }
}
