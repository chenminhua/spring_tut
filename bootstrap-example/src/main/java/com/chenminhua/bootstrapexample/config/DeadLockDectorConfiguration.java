package com.chenminhua.bootstrapexample.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class DeadLockDectorConfiguration {

    static class DeadLockDetector {


        void detect() {
            ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();

            Runnable dlCheck = new Runnable() {
                @Override
                public void run() {
                    long[] threadIds = mxBean.findDeadlockedThreads();
                    System.out.println("detect deadlock");
                    if (threadIds != null) {
                        ThreadInfo[] threadInfos = mxBean.getThreadInfo(threadIds);
                        System.out.println("detected deadlock thread:");
                        for (ThreadInfo threadInfo : threadInfos) {
                            System.out.println(threadInfo.getThreadName());
                        }
                    }
                }
            };
            ScheduledExecutorService sch = Executors.newScheduledThreadPool(1);
            sch.scheduleAtFixedRate(dlCheck, 5L, 10L, TimeUnit.SECONDS);
        }

    }

    @Bean
    @ConditionalOnProperty(
            name = "detectDeadLock",
            havingValue = "true"
    )
    DeadLockDetector getDeadLockDetector() {
        DeadLockDetector d = new DeadLockDetector();
        d.detect();
        return d;
    }

}
