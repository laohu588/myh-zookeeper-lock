package com.myh.zookeeper.lock.core;

import com.myh.zookeeper.lock.core.zk.ZookeeperAbstractLock;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * 实现分布式zookeeper加锁处理;
 * 
 * @author myh
 * @date 2019/09/06
 * @copyright copyright (c) 2019
 */
public class ZookeeperDistruteLock extends ZookeeperAbstractLock {

    // 定义一个发令枪对象;
    private CountDownLatch countDownLatch = null;

    public ZookeeperDistruteLock() {
    }

    /**
     * 创建锁对象;
     *
     * @param address zookeeper地址
     * @param lockKey 临时节点名称;
     */
    public ZookeeperDistruteLock(String address, String lockKey) {
        super.zkProperties.setAddress(address);
        super.zkProperties.setLockKey(lockKey);
        super.zkClient = new ZkClient(address, 2000, 1000);
    }

    /**
     * 创建锁对象，带超时时间控制;
     *
     * @param address
     * @param lockKey
     * @param sessionTimeout
     * @param connectionTimeout
     */
    public ZookeeperDistruteLock(String address, String lockKey, int sessionTimeout, int connectionTimeout) {
        super.zkProperties.setAddress(address);
        super.zkProperties.setLockKey(lockKey);
        super.zkClient = new ZkClient(address, sessionTimeout, connectionTimeout);
    }

    /**
     * 非阻塞，获取锁;
     */
    @Override
    public boolean tryLock() {
        try {
            // 创建临时节点，可以防止出现死锁的问题，使用永久节点，容易出现死锁;
            zkClient.createEphemeral(zkProperties.getLockKey());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 通过监听的方式，优雅的实现阻塞式锁;<br>
     * 所谓优雅的实现，指的就是省出很多资源，定期去查看锁资源是否被释放。
     */
    @Override
    public void waitLock() {

        IZkDataListener izkDataListener = new IZkDataListener() {

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }
        };

        // 节点注册事件;
        zkClient.subscribeDataChanges(zkProperties.getLockKey(), izkDataListener);

        // 如果节点存在;
        if (zkClient.exists(zkProperties.getLockKey())) {
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 删除监听;
        zkClient.unsubscribeDataChanges(zkProperties.getLockKey(), izkDataListener);

    }

    /**
     * 释放锁;
     */
    @Override
    public void unlock() {

        if (zkClient != null) {
            zkClient.delete(zkProperties.getLockKey());
        }

    }

}
