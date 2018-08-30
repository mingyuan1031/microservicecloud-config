package com.lwxf.newstore.webapp.baseservice.cache.service.impl;

import javax.annotation.Resource;

import java.util.Collection;

import com.lwxf.newstore.webapp.baseservice.cache.RedisKeyGenerator;
import com.lwxf.newstore.webapp.baseservice.cache.RedisLock;
import com.lwxf.newstore.webapp.baseservice.cache.RedisLock.LockCallback;
import com.lwxf.newstore.webapp.baseservice.cache.RedisLockHandler;
import com.lwxf.newstore.webapp.baseservice.cache.RedisUtils;
import com.lwxf.newstore.webapp.baseservice.cache.service.BaseCacheService;

/**
 * 功能：缓存操作基类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class BaseCacheServiceImpl implements BaseCacheService {
//	protected final ThreadLocal<Map<String[], String>> cachedKey = new ThreadLocal<>();
	@Resource(name = "defaultRedisKeyGenerator")
	protected RedisKeyGenerator<String> redisKeyGenerator;
	@Resource(name = "redisUtils")
	protected RedisUtils redisUtils;
	@Resource(name = "redisLock")
	protected RedisLock redisLock;
	@Resource(name = "redisLockHandlerImpl")
	protected RedisLockHandler redisLockHandler;

	@Override
	public void setExpire(String key, long expire) {
		redisUtils.setExpire(key, expire);
	}

	@Override
	public void deleteCache(String key) {
		redisUtils.del(key);
	}

	@Override
	public void deleteCache(Collection<String> keys) {
		redisUtils.del(keys);
	}

	public Boolean isExist(String key, String hashKey) {
		return redisUtils.hExists(key, hashKey);
	}

	@Override
	public Boolean cacheExist(String cacheKey) {
		return redisUtils.isExist(cacheKey);
	}

	/**
	 * 带锁的redis操作，防止并发时出现数据库数据与缓存数据不一致
	 *
	 * @param key          需要加锁的key
	 * @param lockCallback redis操作回调
	 */
	@Override
	public void lock(String key, LockCallback lockCallback) {
		redisLock.lock(key, lockCallback);
	}


	@Override
	public String generateKey(String... id) {
		/*Map<String[], String> keyMap = cachedKey.get();
		if (keyMap == null) {
			keyMap = new HashMap<>();
			cachedKey.set(keyMap);
		}
		String key = keyMap.get(id);
		if (key == null) {
			key = redisKeyGenerator.generate(getCacheName(), id);
			keyMap.put(id, key);
		}*/
		return redisKeyGenerator.generate(getCacheName(), id);
	}

	protected abstract String getCacheName();


}
