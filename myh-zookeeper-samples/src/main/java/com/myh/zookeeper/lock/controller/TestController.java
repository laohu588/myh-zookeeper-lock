package com.myh.zookeeper.lock.controller;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myh.zookeeper.lock.abstracts.Lock;
import com.myh.zookeeper.lock.core.ZookeeperDistruteLock;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {

	private Lock lock = new ZookeeperDistruteLock("zk1.zk.test.duia.com", "myh_lock001");

	private AtomicInteger count = new AtomicInteger(0);

	@SuppressWarnings("static-access")
	@RequestMapping("/lock")
	public String lock() {

		count.addAndGet(1);

		log.info(">>> 第{}次，进行加锁操作：{},线程名：{}", count.get(), new Date(System.currentTimeMillis()),Thread.currentThread().getName());

		try {

			lock.lock();
			log.info("1、加锁成功");

			log.info("2、处理完成业务中......");

			try {
				Thread.currentThread().sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			log.info("3、处理完成业务结束");

		} finally {
			lock.unlock();
			log.info("4、释放锁");
		}

		return "200";

	}

}
