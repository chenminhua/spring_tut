package com.chenminhua.aopexp;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class LimitingAop {

    private ConcurrentHashMap<String, Counter> counterMap = new ConcurrentHashMap<String, Counter>();

    @Pointcut("@annotation(com.chenminhua.aopexp.LimitingAnnotation)")
    public void annotationPointCut(){}

    @Around(value="annotationPointCut()")
    Object handler(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

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


//@Aspect
//@Component
//class LimitingAop{
//
//
//    fun parseAnnotation(pjp: ProceedingJoinPoint): LimitingAnnotation {
//        val method = (pjp.signature as MethodSignature).method
//        return method.getAnnotation(LimitingAnnotation::class.java)
//    }
//
//    fun resetCounter(requestPath: String) {
//        counterMap.put(requestPath, Counter(AtomicInteger(1), Date().time))
//    }
//
//    fun getRequestPath(request: HttpServletRequest): String {
//        val uri = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE) as String
//        return "${uri}@[${request.method}]"
//
//    }

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
//}
