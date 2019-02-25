package com.chenminhua.awarebeanexp;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
public class FooService implements SmartLifecycle {
    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable runnable) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
