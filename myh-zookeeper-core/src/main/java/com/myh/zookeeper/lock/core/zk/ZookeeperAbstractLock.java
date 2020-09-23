package com.myh.zookeeper.lock.core.zk;

import org.I0Itec.zkclient.ZkClient;

import com.myh.zookeeper.lock.abstracts.AbstractLock;
import com.myh.zookeeper.lock.properties.ZkProperties;

public abstract class ZookeeperAbstractLock extends AbstractLock {

    protected ZkProperties zkProperties = new ZkProperties();

    protected ZkClient zkClient;

}
