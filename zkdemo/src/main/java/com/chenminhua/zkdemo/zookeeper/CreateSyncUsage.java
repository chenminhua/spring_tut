package com.chenminhua.zkdemo.zookeeper;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

public class CreateSyncUsage implements Watcher {
    private static final CountDownLatch connectedLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            connectedLatch.countDown();
        }
    }

    public static void test() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(
                "localhost:2181",
                5000, new CreateSyncUsage());
        connectedLatch.await();

        // 创建临时节点，返回path就是传入path
        String path1 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create znode: " + path1);

        // 创建临时顺序节点，返回path会自动在节点后缀加上一个数字。
        String path2 = zooKeeper.create("/zk-test-ephemeral-", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create znode: " + path2);
        zooKeeper.close();
    }
}
