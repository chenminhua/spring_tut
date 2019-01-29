package com.chenminhua.aopexp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE_PARAMETER})
public @interface LimitingAnnotation {
    int intervalTime() default 6000; // 默认 1 分钟 100 次
    int maxCount() default 100;
}
