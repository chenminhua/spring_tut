package com.chenminhua.awarebeanexp;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class DemoApp {

    public interface Boom {}
    public static class Boom1 implements Boom {}
    public static class Boom2 implements Boom {}

    @Bean
    BoomFactoryBean boom() {
        return new BoomFactoryBean();
    }

    public static class BoomFactoryBean implements FactoryBean<Boom> {

        private boolean preferOne = true;
        public void setPreferOne(boolean preferOne) {
            this.preferOne = preferOne;
        }

        @Override
        public Boom getObject() throws Exception {
            return this.preferOne ? new Boom1() : new Boom2();
        }

        @Override
        public Class<?> getObjectType() {
            return null;
        }

        @Override
        public boolean isSingleton() {
            return false;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class);
    }
}

class Foo implements InitializingBean, DisposableBean {
    public Bar bar;

    public Foo() { }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @PostConstruct
    public void init() {
        System.out.println("------------post construct------------");
    }

    @PreDestroy
    public void destory() {
        System.out.println("------------pre destroy------------");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //Assert.notNull(this.bar, "bar can't be null");
        System.out.println("------------after properties set------------");
    }

    @Override
    public void destroy() throws Exception {
        this.bar = null;
    }
}

class Bar {
}
