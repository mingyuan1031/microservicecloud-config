package com.lwxf.newstore.webapp.baseservice.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 功能：redis分布式锁接口定义
 * 来源：http://blog.csdn.net/zhu_tianwei/article/details/44927331
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface RedisLockHandler {
	/**
	 * 获取锁  如果锁可用   立即返回true，  否则返回false
	 * @param key
	 * @return
	 */
	boolean tryLock(String key);
	/**
	 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	boolean tryLock(String key, long timeout, TimeUnit unit);

	/**
	 * 如果锁空闲立即返回   获取失败 一直等待
	 * @param key
	 */
	void lock(String key);

	/**
	 * 批量获取锁  如果全部获取   立即返回true, 部分获取失败 返回false
	 * @param keyList
	 * @return
	 */
	boolean tryLock(List<String> keyList);

	/**
	 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
	 * @param keyList
	 * @param timeout
	 * @param unit
	 * @return
	 */
	boolean tryLock(List<String> keyList, long timeout, TimeUnit unit);


	/**
	 * 释放锁
	 * @param key
	 */
	void unLock(String key);
	/**
	 * 批量释放锁
	 * @param keyList
	 */
	void unLock(List<String> keyList);
}
