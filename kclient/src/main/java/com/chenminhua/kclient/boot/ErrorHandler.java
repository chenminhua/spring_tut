package com.chenminhua.kclient.boot;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ErrorHandler {
    Class<? extends Throwable> exception() default Throwable.class;

    String topic() default "";
}