package com.chenminhua.aopexp;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个简单的限流aop
 */

@Aspect
@Component
public class LimitingAop {

    private ConcurrentHashMap<String, Counter> counterMap = new ConcurrentHashMap<String, Counter>();

    @Pointcut("@annotation(com.chenminhua.aopexp.LimitingAnnotation)")
    public void annotationPointCut(){}

    @Around(value="annotationPointCut()")
    Object handler(ProceedingJoinPoint pjp) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest();
            String requestPath = getRequestPath(request);
            LimitingAnnotation annotation = parseAnnotation(pjp);
            int intervalTime = annotation.intervalTime();
            int maxCount = annotation.maxCount();

            Counter counter = counterMap.get(requestPath);
            if (counter == null) {
                resetCounter(requestPath);
            } else {
                Long now = (new Date()).getTime();
                Long lastTime = counter.lastResetTime;
                AtomicInteger count = counter.count;
                long diffTime = now - lastTime;
                if (diffTime > intervalTime) {
                    resetCounter(requestPath);
                } else {
                    count.incrementAndGet();
                    if (count.get() > maxCount) {
                        throw new Exception("请求过于频繁");
                    }
                }
            }
            return pjp.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void resetCounter(String requestPath) {
        counterMap.put(requestPath, new Counter(new AtomicInteger(1), (new Date()).getTime()));
    }

    private LimitingAnnotation parseAnnotation(ProceedingJoinPoint pjp) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        return method.getAnnotation(LimitingAnnotation.class);
    }

    private String getRequestPath(HttpServletRequest request) {
        String uri = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return uri + "@" + request.getMethod();
    }


//    @Around(value = "annotationPointCut()")
//    fun hander(pjp: ProceedingJoinPoint): Any?{
//        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
//        val requestPath = getRequestPath(request)
//        val annotation = parseAnnotation(pjp)
//        val intervalTime = annotation.intervalTime
//        val maxCount = annotation.maxCount
//
//        val counter = counterMap.get(requestPath)
//        if (counter == null) {
//            resetCounter(requestPath)
//        } else {
//            val now = Date().time
//            val lastTime = counter.lastResetTime
//            val count = counter.count
//            val diffTime = now - lastTime
//            if (diffTime > intervalTime) {
//                resetCounter(requestPath)
//            } else {
//                count.incrementAndGet()
//                if (count.get() > maxCount) {
//                    //println("exception $requestPath $count")
//                    throw BadRequest("$requestPath 请求过于频繁，请稍等")
//                }
//            }
//        }
//
//        return pjp.proceed()
//    }

    static class Counter {
        private AtomicInteger count;
        private Long lastResetTime;

        Counter() {}

        Counter(AtomicInteger count, Long lastResetTime) {
            this.count = count;
            this.lastResetTime = lastResetTime;
        }

        public AtomicInteger getCount() {
            return count;
        }

        public void setCount(AtomicInteger count) {
            this.count = count;
        }

        public Long getLastResetTime() {
            return lastResetTime;
        }

        public void setLastResetTime(Long lastResetTime) {
            this.lastResetTime = lastResetTime;
        }
    }
}

