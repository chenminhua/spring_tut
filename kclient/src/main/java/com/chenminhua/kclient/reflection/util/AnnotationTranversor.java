package com.chenminhua.kclient.reflection.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * this is the main class to traverse a class definition and provide any declared annotation for annotationHandler
 * @param <C> category type for the data entry
 * @param <K> key type for the data entry
 * @param <V> value type for the data entry
 */
public class AnnotationTranversor<C,K,V> {
    Class<? extends Object> clazz;
    public AnnotationTranversor(Class<? extends Object> clazz) {
        this.clazz = clazz;
    }

    public Map<C, Map<K, V>> traverseAnnotation(AnnotationHandler<C, K, V> annotationHandler) {
        TraversorContext<C, K, V> ctx = new TraversorContext<>();
        for (Annotation annotation : clazz.getAnnotations()) {
            annotationHandler.handleClassAnnotation(clazz, annotation, ctx);
        }

        for (Method method : clazz.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                annotationHandler.handleMethodAnnotation(clazz, method, annotation, ctx);
            }
        }
        return ctx.getData();

    }
}
