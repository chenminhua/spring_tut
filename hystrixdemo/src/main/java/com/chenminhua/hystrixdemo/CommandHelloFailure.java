package com.chenminhua.hystrixdemo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Future;

public class CommandHelloFailure extends HystrixCommand<String> {

    private final String name;

    public CommandHelloFailure(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        throw new RuntimeException("this command always fails");
    }

    @Override
    protected String getFallback() {
        return "Hello Failure " + name + "!";
    }

    public static class UnitTest {
        @Test
        public void testSynchronous() {
            Assert.assertEquals("Hello Failure World!", new CommandHelloFailure("World").execute());
        }

        @Test
        public void testAsync() throws Exception {
            Future<String> f = new CommandHelloFailure("World").queue();
            Assert.assertEquals("Hello Failure World!", f.get());

        }
    }
}
