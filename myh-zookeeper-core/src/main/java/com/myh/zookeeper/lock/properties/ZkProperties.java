package com.myh.zookeeper.lock.properties;

public class ZkProperties {

    // zkServers地址
    private String address;

    // 临时节点锁的key的名称(临时节点可以防止死锁的问题;)
    private String lockKey;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLockKey() {
        return "/" + lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

}
