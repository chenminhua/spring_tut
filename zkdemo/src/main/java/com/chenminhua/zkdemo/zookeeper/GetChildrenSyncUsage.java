package com.chenminhua.zkdemo.zookeeper;

import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GetChildrenSyncUsage implements Watcher {
    private static CountDownLatch connectLatch = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void test() throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181", 5000, new GetChildrenSyncUsage());
        connectLatch.await();
        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        List<String> childrenList = zk.getChildren(path, true);
        System.out.println(childrenList);

        zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                connectLatch.countDown();
            } else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                try {
                    System.out.println("Reget child: " + zk.getChildren(watchedEvent.getPath(), true));
                } catch (Exception e) {}
            }
        }
    }
}
