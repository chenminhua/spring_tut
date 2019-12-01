package com.chenminhua.zkdemo.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class SetDataAsyncUsage implements Watcher {

    private static CountDownLatch connectLatch = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void test() throws Exception{
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181", 5000, new SetDataAsyncUsage());
        connectLatch.await();

        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.setData(path, "456".getBytes(), -1, new IStatCallback(), null);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                connectLatch.countDown();
            }
        }
    }
}

class IStatCallback implements AsyncCallback.StatCallback {

    @Override
    public void processResult(int i, String s, Object o, Stat stat) {
        if (i == 0) {
            System.out.println("Success");
        }
    }
}
