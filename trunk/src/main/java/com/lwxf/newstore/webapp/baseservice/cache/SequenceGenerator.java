package com.lwxf.newstore.webapp.baseservice.cache;

import javax.annotation.Resource;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

/**
 * 功能：自增序列生成器
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-08-17 8:45
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SequenceGenerator {
	@Resource(name = "notSupportTransactionRedisTemplate")
	RedisTemplate<String, String> redisTemplate;

	/**
	 * @param key
	 * @param value
	 * @Title: set
	 * @Description: set cache.
	 */
	public void set(String key, int value) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		counter.set(value);
	}

	/**
	 * @param key
	 * @param value
	 * @param expireTime
	 * @Title: set
	 * @Description: set cache.
	 */
	public void set(String key, int value, Date expireTime) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		counter.set(value);
		counter.expireAt(expireTime);
	}

	/**
	 * @param key
	 * @param value
	 * @param timeout
	 * @param unit
	 * @Title: set
	 * @Description: set cache.
	 */
	public void set(String key, int value, long timeout, TimeUnit unit) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		counter.set(value);
		counter.expire(timeout, unit);
	}

	/**
	 * @param key
	 * @return
	 * @Title: generate
	 * @Description: Atomically increments by one the current value.
	 */
	public long generate(String key) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		return counter.incrementAndGet();
	}

	/**
	 * @param key
	 * @return
	 * @Title: generate
	 * @Description: Atomically increments by one the current value.
	 */
	public long generate(String key, Date expireTime) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		counter.expireAt(expireTime);
		return counter.incrementAndGet();
	}

	/**
	 * @param key
	 * @param increment
	 * @return
	 * @Title: generate
	 * @Description: Atomically adds the given value to the current value.
	 */
	public long generate(String key, int increment) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		return counter.addAndGet(increment);
	}

	/**
	 * @param key
	 * @param increment
	 * @param expireTime
	 * @return
	 * @Title: generate
	 * @Description: Atomically adds the given value to the current value.
	 */
	public long generate(String key, int increment, Date expireTime) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		counter.expireAt(expireTime);
		return counter.addAndGet(increment);
	}

	/**
	 * @param key
	 * @return
	 * @Title: generate
	 * @Description: Atomically increments by one the current value.
	 */
	public long generate(String key,long expire,TimeUnit timeUnit) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		counter.expire(expire,timeUnit);
		return counter.incrementAndGet();
	}

	/**
	 * 根据key删除编号缓存
	 * @param key
	 * @return
	 */
	/*public boolean delete(String key){
		return this.redisTemplate.delete(key).booleanValue();
	}*/
}
