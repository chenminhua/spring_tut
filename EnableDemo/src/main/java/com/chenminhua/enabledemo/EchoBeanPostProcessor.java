package com.chenminhua.enabledemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.List;

public class EchoBeanPostProcessor implements BeanPostProcessor {

    private List<String> packages;

    //该方法在spring容器初始化前执行
    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
        for (String pack : packages) {
            if (bean.getClass().getName().startsWith(pack)) {
                System.out.println("echo bean: " + bean.getClass().getName());
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        return bean;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }
}
