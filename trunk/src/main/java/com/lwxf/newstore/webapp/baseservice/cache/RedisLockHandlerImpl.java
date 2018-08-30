package com.lwxf.newstore.webapp.baseservice.cache;

import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.annotation.Resource;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import com.lwxf.newstore.webapp.baseservice.cache.constant.RedisConstant;

/**
 * 功能： redis 分布式锁实现
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("redisLockHandlerImpl")
public class RedisLockHandlerImpl implements RedisLockHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockHandlerImpl.class);

	private static final String LOCK_PREFIX = RedisConstant.LOCK_PREFIX;

	// 单个锁有效期
	private static final int DEFAULT_SINGLE_EXPIRE_TIME = RedisConstant.DEFAULT_SINGLE_EXPIRE_TIME / 1000;
	// 批量锁有效期
	private static final int DEFAULT_BATCH_EXPIRE_TIME = RedisConstant.DEFAULT_BATCH_EXPIRE_TIME / 1000;

	@Resource(name = "jedisConnectionFactory")
	private RedisConnectionFactory redisConnectionFactory;

	/**
	 * 构造
	 */
	public RedisLockHandlerImpl() {
	}

	/**
	 * 获取锁 如果锁可用 立即返回true， 否则返回false，不等待
	 *
	 * @return
	 */
	@Override
	public boolean tryLock(String key) {
		return tryLock(key, 0L, null);
	}

	/**
	 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
	 *
	 * @param timeout
	 * @param unit
	 * @return
	 */
	@Override

	public boolean tryLock(String key, long timeout, TimeUnit unit) {
		RedisConnection redisConnection = getRedisConnection();
		key = LOCK_PREFIX + key;
		try {
			//系统计时器的当前值，以毫微秒为单位。
			long nano = System.nanoTime();
			do {
				LOGGER.debug("try lock key: " + key);
				byte[] bytesKey = key.getBytes(Charset.defaultCharset());
				//将 key 的值设为 value 1成功  0失败
				Boolean result = redisConnection.setNX(bytesKey, bytesKey);
				if (result) {
					redisConnection.expire(bytesKey, DEFAULT_SINGLE_EXPIRE_TIME);
					LOGGER.debug("get lock, key: " + key + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
					//成功获取锁，返回true
					return true;
				}
				/*else { // 存在锁,循环等待锁
					if (LOGGER.isDebugEnabled()) {
						String desc = (String) redisUtils.get(key);
						LOGGER.debug("key: " + key + " locked by another business：" + desc);
					}
				}*/
				if (timeout <= 0) {
					//没有设置超时时间，直接退出等待
					break;
				}
				Thread.sleep(300);
			} while ((System.nanoTime() - nano) < unit.toNanos(timeout));
			System.out.println("time out");
			return false;
		} catch (JedisConnectionException je) {
			LOGGER.error("连接异常", je);
		} catch (Exception e) {
			LOGGER.error("获取锁发生异常", e);
		} finally {
			closeConnection(redisConnection);
		}
		return false;
	}

	/**
	 * 如果锁空闲立即返回 获取失败 一直等待
	 */
	@Override
	public void lock(String key) {
		RedisConnection redisConnection = getRedisConnection();
		key = LOCK_PREFIX + key;
		try {
			do {
				LOGGER.debug("lock key: {}", key);
				byte[] bytesKey = key.getBytes(Charset.defaultCharset());
				Boolean result = redisConnection.setNX(bytesKey, bytesKey);
				if (result) {
					redisConnection.expire(bytesKey, DEFAULT_SINGLE_EXPIRE_TIME);
					LOGGER.debug("get lock, key: {} , expire in {} seconds.", key, DEFAULT_SINGLE_EXPIRE_TIME);
					return;
				} else {
					if (LOGGER.isDebugEnabled()) {
						String desc = new String(redisConnection.get(bytesKey), Charset.defaultCharset());
						LOGGER.debug("key: {} locked by another business：{}", key, desc);
					}
				}
				Thread.sleep(300);
			} while (true);
		} catch (JedisConnectionException je) {
			LOGGER.error("连接异常", je);
		} catch (Exception e) {
			LOGGER.error("加锁时发生异常", e);
		} finally {
			closeConnection(redisConnection);
		}
	}

	/**
	 * 释放锁
	 */
	@Override
	public void unLock(String key) {
		List<String> list = new ArrayList<String>();
		list.add(key);
		unLock(list);
	}

	/**
	 * 批量获取锁 如果全部获取 立即返回true, 部分获取失败 返回false
	 *
	 * @return
	 */
	@Override
	public boolean tryLock(List<String> keyList) {
		return tryLock(keyList, 0L, null);
	}

	/**
	 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
	 *
	 * @param timeout
	 * @param unit
	 * @return
	 */
	@Override
	public boolean tryLock(List<String> keyList, long timeout, TimeUnit unit) {
		RedisConnection redisConnection = getRedisConnection();
		try {
			//需要的锁
			List<String> needLocking = new CopyOnWriteArrayList<String>();
			//得到的锁
			List<String> locked = new CopyOnWriteArrayList<String>();
			long nano = System.nanoTime();
			do {
				// 构建pipeline，批量提交
				redisConnection.openPipeline();
				for (String key : keyList) {
					key = LOCK_PREFIX + key;
					needLocking.add(key);
					byte[] bytesKey = key.getBytes(Charset.defaultCharset());
					redisConnection.setNX(bytesKey, bytesKey);
				}
				List<Object> results = redisConnection.closePipeline();
				LOGGER.debug("try lock keys: {}" ,needLocking);
				// 提交redis执行计数,批量处理完成返回
				for (int i = 0; i < results.size(); ++i) {
					Boolean result = (Boolean) results.get(i);
					String key = needLocking.get(i);
					if (result) { // setnx成功，获得锁
						redisConnection.expire(key.getBytes(Charset.defaultCharset()), DEFAULT_BATCH_EXPIRE_TIME);
						locked.add(key);
					}
				}
				needLocking.removeAll(locked); // 已锁定资源去除

				if (needLocking.isEmpty()) { //成功获取全部的锁
					return true;
				} else {
					// 部分资源未能锁住
					LOGGER.debug("keys: {} locked by another business：", needLocking);
				}

				if (timeout == 0) {
					break;
				}
				Thread.sleep(500);
			} while ((System.nanoTime() - nano) < unit.toNanos(timeout));

			// 得不到锁，释放锁定的部分对象，并返回失败
			if (locked.size() > 0) {
				int len = locked.size();
				byte[][] keys = new byte[len][];
				for (int i = 0; i < len; i++) {
					keys[i] = locked.get(i).getBytes(Charset.defaultCharset());
				}
				redisConnection.del(keys);
			}
			return false;
		} catch (JedisConnectionException je) {
			LOGGER.error("连接异常", je);
		} catch (Exception e) {
			LOGGER.error("获取锁时发生异常", e);
		} finally {
			closeConnection(redisConnection);
		}
		return true;
	}

	/**
	 * 批量释放锁
	 */
	@Override
	public void unLock(List<String> keyList) {
		RedisConnection redisConnection = getRedisConnection();
		int len = keyList.size();
		byte[][] keys = new byte[len][];
		for (int i = 0; i < len; i++) {
			keys[i] = (LOCK_PREFIX + keyList.get(i)).getBytes(Charset.defaultCharset());
		}
		try {
			redisConnection.del(keys);
//			LOGGER.debug("release lock, keys :" + keys);
		} catch (JedisConnectionException je) {
			LOGGER.error("连接异常", je);
		} catch (Exception e) {
			LOGGER.error("释放锁时发生异常", e);
		} finally {
			closeConnection(redisConnection);
		}
	}

	public RedisConnection getRedisConnection() {
		return redisConnectionFactory.getConnection();
	}

	public void closeConnection(RedisConnection redisConnection) {
		if (redisConnection != null && !redisConnection.isClosed()) {
			redisConnection.close();
		}
	}

}
