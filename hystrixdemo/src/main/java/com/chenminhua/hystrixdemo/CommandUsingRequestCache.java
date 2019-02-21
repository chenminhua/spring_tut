package com.chenminhua.hystrixdemo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Assert;
import org.junit.Test;

public class CommandUsingRequestCache extends HystrixCommand<Boolean> {

    private final int value;

    protected CommandUsingRequestCache(int value) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.value = value;
    }


    @Override
    protected Boolean run() throws Exception {
        return value == 0 || value % 2 == 0;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(value);
    }

    public static class UnitTest {

        @Test
        public void testWithoutCacheHits() {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
                Assert.assertTrue(new CommandUsingRequestCache(2).execute());
                Assert.assertFalse(new CommandUsingRequestCache(1).execute());
                Assert.assertTrue(new CommandUsingRequestCache(0).execute());
                Assert.assertTrue(new CommandUsingRequestCache(58672).execute());
            } finally {
                context.shutdown();
            }
        }

        @Test
        public void testWithCacheHits() {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
                CommandUsingRequestCache command2a = new CommandUsingRequestCache(2);
                CommandUsingRequestCache command2b = new CommandUsingRequestCache(2);

                Assert.assertTrue(command2a.execute());
                Assert.assertFalse(command2a.isResponseFromCache());

                Assert.assertTrue(command2b.execute());
                Assert.assertTrue(command2b.isResponseFromCache());


            } finally {
                context.shutdown();
            }

            context = HystrixRequestContext.initializeContext();
            try {
                CommandUsingRequestCache command3b = new CommandUsingRequestCache(2);
                Assert.assertTrue(command3b.execute());
                // this is a new request context so this should not come from cache
                Assert.assertFalse(command3b.isResponseFromCache());
            } finally {
                context.shutdown();
            }
        }
    }
}
