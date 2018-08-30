package com.lwxf.newstore.webapp.baseservice.cache;

import java.util.concurrent.TimeUnit;

import static com.lwxf.newstore.webapp.facade.AppBeanInjector.redisUtils;

/**
 * 功能：redis攻击锁
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class RedisAttackLock {
	/**
	 * 检查是否已经锁定
	 * @param key
	 * @param limit
	 * @param expire
	 * @param timeUnit
	 * @return
	 */
	public static boolean checkLocked(String key, int limit, int expire, TimeUnit timeUnit){
		int currentNumber = redisUtils.getInt(key);
		if(currentNumber>=limit){
			return true;
		}else {
			redisUtils.increment(key);
			redisUtils.setExpire(key,expire,timeUnit);
		}
		return false;
	}
}
