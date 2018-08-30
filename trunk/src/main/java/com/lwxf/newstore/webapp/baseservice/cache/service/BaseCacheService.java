package com.lwxf.newstore.webapp.baseservice.cache.service;

import java.util.Collection;

import com.lwxf.newstore.webapp.baseservice.cache.RedisLock.LockCallback;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface BaseCacheService {
	void setExpire(String key, long expire);

	String generateKey(String... id);

	void deleteCache(String cacheName);

	void deleteCache(Collection<String> cacheName);

	Boolean cacheExist(String cacheKey);

	void lock(String key, LockCallback lockCallback);

}
