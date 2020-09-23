package com.myh.zookeeper.lock.abstracts;

/**
 * 定义锁的接口;
 * 
 * @author myh
 * @date 2019/11/19
 * @copyright copyright (c) 2019
 */
public interface Lock {

    /**
     * 获取锁;
     */
    public void lock();

    /**
     * 释放锁;
     */
    public void unlock();

}
