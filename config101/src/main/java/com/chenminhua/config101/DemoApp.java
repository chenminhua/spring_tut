package com.chenminhua.config101;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

@Configuration
public class DemoApp {

    @Component
    public static class MyBDRPP implements BeanDefinitionRegistryPostProcessor {

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry bdr) throws BeansException {

            BeanFactory bf = BeanFactory.class.cast(bdr);

            bdr.registerBeanDefinition("barService",
                    BeanDefinitionBuilder.genericBeanDefinition(BarService.class).getBeanDefinition());

            bdr.registerBeanDefinition("fooService",
                    BeanDefinitionBuilder.genericBeanDefinition(FooService.class,
                            () -> new FooService(bf.getBean(BarService.class))).getBeanDefinition());
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        }
    }

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(DemoApp.class);
        ProgrammaticBeanDefinitionInitializer initializer = new ProgrammaticBeanDefinitionInitializer();
        initializer.initialize((GenericApplicationContext) ac);

    }
}

class FooService {
    private final BarService barService;

    FooService(BarService barService) {
        this.barService = barService;
    }
}

class BarService {}

class ProgrammaticBeanDefinitionInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(GenericApplicationContext genericApplicationContext) {
        genericApplicationContext.registerBean(BarService.class);
        genericApplicationContext.registerBean(FooService.class,
                () -> new FooService(genericApplicationContext.getBean(BarService.class)));

    }
}
