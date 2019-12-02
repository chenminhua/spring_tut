package com.chenminhua.kclient.boot;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InputConsumer {
    String propertiesFile() default "";

    String topic() default "";

    int streamNum() default 1;

    int fixedThreadNum() default 0;

    int minThreadNum() default 0;

    int maxThreadNum() default 0;
}
