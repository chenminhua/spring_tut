package com.chenminhua.kclient.boot;

import com.chenminhua.kclient.core.KafkaConsumer;
import com.chenminhua.kclient.core.KafkaProducer;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;

import java.util.List;

public class KafkaHandler {
    private KafkaConsumer kafkaConsumer;

    private KafkaProducer kafkaProducer;

    private List<ExceptionHandler> excepHandlers;

    private KafkaHandlerMeta kafkaHandlerMeta;

    public KafkaHandler(KafkaConsumer kafkaConsumer,
                        KafkaProducer kafkaProducer, List<ExceptionHandler> excepHandlers,
                        KafkaHandlerMeta kafkaHandlerMeta) {
        super();
        this.kafkaConsumer = kafkaConsumer;
        this.kafkaProducer = kafkaProducer;
        this.excepHandlers = excepHandlers;
        this.kafkaHandlerMeta = kafkaHandlerMeta;
    }

    public KafkaConsumer getKafkaConsumer() {
        return kafkaConsumer;
    }

    public void setKafkaConsumer(KafkaConsumer kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public KafkaProducer getKafkaProducer() {
        return kafkaProducer;
    }

    public void setKafkaProducer(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public List<ExceptionHandler> getExcepHandlers() {
        return excepHandlers;
    }

    public void setExcepHandlers(List<ExceptionHandler> excepHandlers) {
        this.excepHandlers = excepHandlers;
    }

    public KafkaHandlerMeta getKafkaHandlerMeta() {
        return kafkaHandlerMeta;
    }

    public void setKafkaHandlerMeta(KafkaHandlerMeta kafkaHandlerMeta) {
        this.kafkaHandlerMeta = kafkaHandlerMeta;
    }

}
