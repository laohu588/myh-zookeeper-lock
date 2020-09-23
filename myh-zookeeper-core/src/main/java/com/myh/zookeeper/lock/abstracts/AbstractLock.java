package com.myh.zookeeper.lock.abstracts;

/**
 * 使用模板设计模式，设计父类主模板实现方法;
 * 
 * @author myh
 * @date 2019/11/19
 * @copyright copyright (c) 2019
 */
public abstract class AbstractLock implements Lock {

    /**
     * 阻塞获取锁;
     */
    @Override
    public void lock() {

        if (tryLock()) {
            return;
        }
        // 竞争资源;
        waitLock();

        // 继续加锁;
        lock();
    }

    /**
     * 非阻塞方式获取锁资源;
     * 
     * @return
     */
    public abstract boolean tryLock();

    /**
     * 竞争资源，等待加锁;
     */
    public abstract void waitLock();

}
