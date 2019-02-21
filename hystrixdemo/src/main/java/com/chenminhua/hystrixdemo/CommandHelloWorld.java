package com.chenminhua.hystrixdemo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;

import java.util.concurrent.Future;

public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name + "!";
    }

    public static class UnitTest {

        @Test
        public void testSynchronous() {
            Assert.assertEquals("Hello World!", new CommandHelloWorld("World").execute());
        }

        @Test
        public void testAsynchronous1() throws Exception {
            Future<String> f = new CommandHelloWorld("World").queue();
            Assert.assertEquals("Hello World!", f.get());
        }

        @Test
        public void testObservable() throws Exception {
            Observable<String> f = new CommandHelloWorld("World").observe();

            // blocking
            Assert.assertEquals("Hello World!", f.toBlocking().single());

            // non-blocking
            f.subscribe(v -> {
                Assert.assertEquals("Hello World!", v);
            });

        }


    }
}
