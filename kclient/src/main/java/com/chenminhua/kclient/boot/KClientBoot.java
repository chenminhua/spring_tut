package com.chenminhua.kclient.boot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chenminhua.kclient.core.KafkaConsumer;
import com.chenminhua.kclient.core.KafkaProducer;
import com.chenminhua.kclient.handlers.MessageHandler;
import com.chenminhua.kclient.reflection.util.AnnotationHandler;
import com.chenminhua.kclient.reflection.util.AnnotationTranversor;
import com.chenminhua.kclient.reflection.util.TraversorContext;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KClientBoot implements ApplicationContextAware {

    protected static Logger log = LoggerFactory.getLogger(KClientBoot.class);
    private ApplicationContext applicationContext;
    private List<KafkaHandlerMeta> meta = new ArrayList<>();
    private List<KafkaHandler> kafkaHandlers = new ArrayList<>();

    public KClientBoot() {}

    public void init() {
        meta = getKafkaHandlerMeta();

        if (meta.size() == 0) {
            throw new IllegalArgumentException(
                    "No handler method is declared in this spring context.");
        }

        for (final KafkaHandlerMeta kafkaHandlerMeta : meta) {
            createKafkaHandler(kafkaHandlerMeta);
        }
    }

    protected List<KafkaHandlerMeta> getKafkaHandlerMeta() {
        List<KafkaHandlerMeta> meta = new ArrayList<>();
        String[] kafkaHandlerBeanNames = applicationContext.getBeanNamesForAnnotation(KafkaHandlers.class);
        for (String beanName : kafkaHandlerBeanNames) {
            Object kafkaHandlerBean = applicationContext.getBean(beanName);
            Class<? extends Object> kafkaHandlerBeanClazz = kafkaHandlerBean.getClass();
            Map<Class<? extends Annotation>, Map<Method, Annotation>> mapData = extractAnnotationMaps(kafkaHandlerBeanClazz);
            meta.addAll(convertAnnotationMaps2Meta(mapData, kafkaHandlerBean));
        }
        return meta;
    }


    private Map<Class<? extends Annotation>, Map<Method, Annotation>> extractAnnotationMaps(Class<?> kafkaHandlerBeanClazz) {
        AnnotationTranversor<Class<? extends Annotation>, Method, Annotation> annotationTranversor =
                new AnnotationTranversor<>(kafkaHandlerBeanClazz);
        Map<Class<? extends Annotation>, Map<Method, Annotation>> data = annotationTranversor
                .traverseAnnotation(new AnnotationHandler<Class<? extends Annotation>, Method, Annotation>() {
                    @Override
                    public void handleMethodAnnotation(Class<?> clazz, Method method, Annotation annotation, TraversorContext<Class<? extends Annotation>, Method, Annotation> context) {
                        if (annotation instanceof InputConsumer) {
                            context.addEntry(InputConsumer.class, method, annotation);
                        } else if (annotation instanceof OutputProducer) {
                            context.addEntry(OutputProducer.class, method, annotation);
                        } else if (annotation instanceof ErrorHandler) {
                            context.addEntry(ErrorHandler.class, method, annotation);
                        }
                    }

                    @Override
                    public void handleClassAnnotation(Class<?> clazz, Annotation annotation, TraversorContext<Class<? extends Annotation>, Method, Annotation> context) {
                        if (annotation instanceof KafkaHandlers) {
                            log.warn(
                                    "There is some other annotation {} rather than @KafkaHandlers in the handler class {}.",
                                    annotation.getClass().getName(),
                                    clazz.getName());
                        }
                    }
                });
        return data;
    }

    private Collection<? extends KafkaHandlerMeta> convertAnnotationMaps2Meta(
            Map<Class<? extends Annotation>, Map<Method, Annotation>> mapData, Object kafkaHandlerBean) {
        List<KafkaHandlerMeta> meta = new ArrayList<>();
        Map<Method, Annotation> inputConsumerMap = mapData.get(InputConsumer.class);
        Map<Method, Annotation> outputProducerMap = mapData.get(OutputProducer.class);
        Map<Method, Annotation> exceptionHandlerMap = mapData.get(ErrorHandler.class);

        // 处理 InputConsumer
        for (Map.Entry<Method, Annotation> entry : inputConsumerMap.entrySet()) {
            InputConsumer inputConsumer = (InputConsumer) entry.getValue();
            KafkaHandlerMeta kafkaHandlerMeta = new KafkaHandlerMeta();
            kafkaHandlerMeta.setBean(kafkaHandlerBean);
            kafkaHandlerMeta.setMethod(entry.getKey());
            Parameter[] kafkaHandlerParameters = entry.getKey().getParameters();
            if (kafkaHandlerParameters.length != 1) {
                throw new IllegalArgumentException("Thre kafka handler method can contains only one parameter");
            }
            kafkaHandlerMeta.setParameterType(kafkaHandlerParameters[0].getType());
            kafkaHandlerMeta.setInputConsumer(inputConsumer);

            if (outputProducerMap != null
                    && outputProducerMap.containsKey(entry.getKey())) {
                kafkaHandlerMeta
                        .setOutputProducer((OutputProducer) outputProducerMap
                                .get(entry.getKey()));
            }

            if (exceptionHandlerMap != null) {
                for (Map.Entry<Method, Annotation> excepHandlerEntry : exceptionHandlerMap
                        .entrySet()) {
                    ErrorHandler eh = (ErrorHandler) excepHandlerEntry
                            .getValue();
                    if (StringUtils.isEmpty(eh.topic())
                            || eh.topic().equals(inputConsumer.topic())) {
                        kafkaHandlerMeta.addErrorHandlers((ErrorHandler) eh,
                                excepHandlerEntry.getKey());
                    }
                }
            }
            meta.add(kafkaHandlerMeta);
        }
        return meta;
    }


    protected void createKafkaHandler(final KafkaHandlerMeta kafkaHandlerMeta) {
        Class<? extends Object> paramClazz = kafkaHandlerMeta
                .getParameterType();

        KafkaProducer kafkaProducer = createProducer(kafkaHandlerMeta);
        List<ExceptionHandler> excepHandlers = createExceptionHandlers(kafkaHandlerMeta);

        MessageHandler beanMessageHandler = null;
        if (paramClazz.isAssignableFrom(JSONObject.class)) {
            beanMessageHandler = createObjectHandler(kafkaHandlerMeta,
                    kafkaProducer, excepHandlers);
        } else if (paramClazz.isAssignableFrom(JSONArray.class)) {
            beanMessageHandler = createObjectsHandler(kafkaHandlerMeta,
                    kafkaProducer, excepHandlers);
        } else if (List.class.isAssignableFrom(Document.class)) {
            beanMessageHandler = createDocumentHandler(kafkaHandlerMeta,
                    kafkaProducer, excepHandlers);
        } else if (List.class.isAssignableFrom(paramClazz)) {
            beanMessageHandler = createBeansHandler(kafkaHandlerMeta,
                    kafkaProducer, excepHandlers);
        } else {
            beanMessageHandler = createBeanHandler(kafkaHandlerMeta,
                    kafkaProducer, excepHandlers);
        }

        KafkaConsumer kafkaConsumer = createConsumer(kafkaHandlerMeta,
                beanMessageHandler);
        kafkaConsumer.startup();

        KafkaHandler kafkaHandler = new KafkaHandler(kafkaConsumer,
                kafkaProducer, excepHandlers, kafkaHandlerMeta);

        kafkaHandlers.add(kafkaHandler);
    }

    private List<ExceptionHandler> createExceptionHandlers(final KafkaHandlerMeta kafkaHandlerMeta) {
        List<ExceptionHandler> excepHandlers = new ArrayList<ExceptionHandler>();
        for (final Map.Entry<ErrorHandler, Method> errorHandler : kafkaHandlerMeta.getErrorHandlers().entrySet()) {

        }
    }

    protected KafkaProducer createProducer(final KafkaHandlerMeta kafkaHandlerMeta) {
        KafkaProducer kafkaProducer = null;

        if (kafkaHandlerMeta.getOutputProducer() != null) {
            kafkaProducer = new KafkaProducer(kafkaHandlerMeta
                    .getOutputProducer().propertiesFile(), kafkaHandlerMeta
                    .getOutputProducer().defaultTopic());
        }
        return kafkaProducer;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void shutdownAll() {
        for (KafkaHandler kafkahandler : kafkaHandlers) {
            kafkahandler.getKafkaConsumer().shutdownGracefully();

            kafkahandler.getKafkaProducer().close();
        }
    }

    public List<KafkaHandler> getKafkaHandlers() {
        return kafkaHandlers;
    }

    public void setKafkaHandlers(List<KafkaHandler> kafkaHandlers) {
        this.kafkaHandlers = kafkaHandlers;
    }
}
