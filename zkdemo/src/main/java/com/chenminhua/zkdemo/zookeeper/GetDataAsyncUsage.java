package com.chenminhua.zkdemo.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class GetDataAsyncUsage implements Watcher {

    private static CountDownLatch connectLatch = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void test() throws Exception {
        String path = "/zh-book";
        zk = new ZooKeeper("localhost:2181", 5000, new GetDataSyncUsage());
        connectLatch.await();

        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.getData(path, true, new IDataCallback(), null);
        zk.setData(path, "123".getBytes(),  -1);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            if (Event.EventType.None == watchedEvent.getType() && watchedEvent.getType() == null) {
                connectLatch.countDown();
            } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                try {
                    zk.getData(watchedEvent.getPath(), true, new IDataCallback(), null);
                } catch (Exception e) {

                }
            }
        }
    }
}

class IDataCallback implements AsyncCallback.DataCallback {

    @Override
    public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
        System.out.println(i + ", " + s + ", " + new String(bytes));
        System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
    }
}
