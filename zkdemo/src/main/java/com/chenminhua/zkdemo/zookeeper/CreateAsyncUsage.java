package com.chenminhua.zkdemo.zookeeper;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

public class CreateAsyncUsage implements Watcher {
    private static CountDownLatch connectedLatch = new CountDownLatch(1);

    public static void test() throws Exception {
        ZooKeeper zk = new ZooKeeper("localhost:2181", 5000,
                new CreateAsyncUsage());
        connectedLatch.await();

        zk.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, new IStringCallback(), "I am context");
        zk.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, new IStringCallback(), "I am context");
        zk.create("/zk-tst-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(), "I am context");
        zk.create("/zk-tst-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(), "I am context");


        Thread.sleep(Integer.MAX_VALUE);
        zk.close();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            connectedLatch.countDown();
        }
    }
}

class IStringCallback implements AsyncCallback.StringCallback{

    @Override
    public void processResult(int i, String s, Object o, String s1) {
        System.out.println("Create path result: [" + i + ", " + s + ", " + o + ", real path name: " + s1);

    }
}
