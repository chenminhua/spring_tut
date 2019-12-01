package com.chenminhua.zkdemo.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class SetDataSyncUsage implements Watcher {
    private static CountDownLatch connectLatch = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void test() throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181", 5000, new SetDataSyncUsage());
        connectLatch.await();

        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.getData(path, true, null);
        // update
        Stat stat = zk.setData(path, "456".getBytes(), -1);
        System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
        Stat stat2 = zk.setData(path, "456".getBytes(), stat.getVersion());
        System.out.println(stat2.getCzxid()+","+stat2.getMzxid()+","+stat2.getVersion());

        // 用老的version试图去更新
        try {
            zk.setData(path, "456".getBytes(),stat.getVersion());
        } catch (KeeperException e) {
            System.out.println("Error: " + e.code() + "," + e.getMessage());
        }
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
