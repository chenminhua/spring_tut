package com.chenminhua.zkdemo.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorDemo {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",
//                5000, 3000, retryPolicy);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("base")
                .build();
        // 创建会话
        client.start();

        String path = "/book/c1";
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath(path, "init".getBytes());

        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        System.out.println("Success set data for: " + path + ", new version: " +
                client.setData().withVersion(stat.getVersion()).forPath(path).getVersion() );

//        client.create().forPath("/book", "123".getBytes());
//        client.create().withMode(CreateMode.EPHEMERAL).forPath("/book"+"/123");
//        Thread.sleep(Integer.MAX_VALUE);
    }
}
