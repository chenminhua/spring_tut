package com.chenminhua.zkdemo.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GetChildrenAsyncUsage implements Watcher {
    private static final CountDownLatch connectLatch = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void test() throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("localhost:2181", 5000, new GetChildrenAsyncUsage());
        connectLatch.await();
        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create(path+"/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.getChildren(path, true, new IChildren2Callback(), null);
        zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                connectLatch.countDown();
            } else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                try {
                    System.out.println("Reget child:" + zk.getChildren(watchedEvent.getPath(), true));
                } catch (Exception e) {}
            }
        }
    }
}

class IChildren2Callback implements AsyncCallback.Children2Callback {

    @Override
    public void processResult(int i, String s, Object o, List<String> list, Stat stat) {
        System.out.println("get children znode result: [response code: "+i+", param path: " + s + ", ctx: " + o +
                ", children list: " + list + ", stat: " + stat);
    }
}
