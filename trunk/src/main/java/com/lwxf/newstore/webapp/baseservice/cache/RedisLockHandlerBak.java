package com.lwxf.newstore.webapp.baseservice.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * 功能： redis 分布式锁实现
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("redisLockHandler")
public class RedisLockHandlerBak implements RedisLockHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockHandlerBak.class);

	// 单个锁有效期
	private static final int DEFAULT_SINGLE_EXPIRE_TIME = 30;
	// 批量锁有效期
	private static final int DEFAULT_BATCH_EXPIRE_TIME = 60;

	private final JedisPool jedisPool;

	/**
	 * 构造
	 */
	public RedisLockHandlerBak() {
		//创建jedis池配置实例
		JedisPoolConfig config = new JedisPoolConfig();
		//设置池配置项值
		config.setMaxTotal(1024);
		config.setMaxIdle(200);
		config.setMaxWaitMillis(1000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);

		//根据配置实例化jedis池
		JedisPool pool = new JedisPool(config, "192.168.1.21", 6379);
		this.jedisPool = pool;
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
		Jedis jedis = null;
		try {
			jedis = getResource();
			//系统计时器的当前值，以毫微秒为单位。
			long nano = System.nanoTime();
			do {
				LOGGER.debug("try lock key: {}", key);
				//将 key 的值设为 value 1成功  0失败
				Long i = jedis.setnx(key, key);
				if (i == 1) {
					//设置过期时间
					jedis.expire(key, DEFAULT_SINGLE_EXPIRE_TIME);
					LOGGER.debug("get lock, key: {} , expire in {} seconds.",key,DEFAULT_SINGLE_EXPIRE_TIME);
					//成功获取锁，返回true
					return true;
				} else { // 存在锁,循环等待锁
					if (LOGGER.isDebugEnabled()) {
						String desc = jedis.get(key);
						LOGGER.debug("key: {} locked by another business：{}",key,desc);
					}
				}
				if (timeout <= 0) {
					//没有设置超时时间，直接退出等待
					break;
				}
				Thread.sleep(300);
			} while ((System.nanoTime() - nano) < unit.toNanos(timeout));
			return false;
		} catch (JedisConnectionException je) {
			LOGGER.error("连接异常", je);
			//释放资源
			returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error("获取锁发生异常", e);
		} finally {
			returnResource(jedis);
		}
		return false;
	}

	/**
	 * 如果锁空闲立即返回 获取失败 一直等待
	 */
	@Override
	public void lock(String key) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			do {
				LOGGER.debug("lock key: {}",key);
				Long i = jedis.setnx(key, key);
				if (i == 1) {
					jedis.expire(key, DEFAULT_SINGLE_EXPIRE_TIME);
					LOGGER.debug("get lock, key: {} , expire in {} seconds.",key,DEFAULT_SINGLE_EXPIRE_TIME);
					return;
				} else {
					if (LOGGER.isDebugEnabled()) {
						String desc = jedis.get(key);
						LOGGER.debug("key: {} locked by another business：{}",key,desc);
					}
				}
				Thread.sleep(300);
			} while (true);
		} catch (JedisConnectionException je) {
			LOGGER.error("连接异常", je);
			returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error("获取锁发生异常", e);
		} finally {
			returnResource(jedis);
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
		Jedis jedis = null;
		try {
			//需要的锁
			List<String> needLocking = new CopyOnWriteArrayList<String>();
			//得到的锁
			List<String> locked = new CopyOnWriteArrayList<String>();
			jedis = getResource();
			long nano = System.nanoTime();
			do {
				// 构建pipeline，批量提交
				Pipeline pipeline = jedis.pipelined();
				for (String key : keyList) {
					needLocking.add(key);
					pipeline.setnx(key, key);
				}
				LOGGER.debug("try lock keys: " + needLocking);
				// 提交redis执行计数,批量处理完成返回
				List<Object> results = pipeline.syncAndReturnAll();
				for (int i = 0; i < results.size(); ++i) {
					Long result = (Long) results.get(i);
					String key = needLocking.get(i);
					if (result == 1) { // setnx成功，获得锁
						jedis.expire(key, DEFAULT_BATCH_EXPIRE_TIME);
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
				jedis.del(locked.toArray(new String[0]));
			}
			return false;
		} catch (JedisConnectionException je) {
			LOGGER.error("连接异常", je);
			returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error("获取锁发生异常", e);
		} finally {
			returnResource(jedis);
		}
		return true;
	}

	/**
	 * 批量释放锁
	 */
	@Override
	public void unLock(List<String> keyList) {
		List<String> keys = new CopyOnWriteArrayList<String>();
		keys.addAll(keyList);
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.del(keys.toArray(new String[0]));
			LOGGER.debug("release lock, keys :" + keys);
		} catch (JedisConnectionException je) {
			LOGGER.error("连接异常", je);
			returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error("获取锁发生异常", e);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 获取redis客户端
	 *
	 * @return
	 */
	private Jedis getResource() {
		return jedisPool.getResource();
	}

	/**
	 * 销毁连接
	 *
	 * @param jedis
	 */
	private void returnBrokenResource(Jedis jedis) {
		if (jedis == null) {
			return;
		}
		try {
			//中断链接
			jedisPool.returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error("释放连接异常", e);
		}
	}

	/**
	 * 重新初始化对象
	 *
	 * @param jedis
	 */
	private void returnResource(Jedis jedis) {
		if (jedis == null) {
			return;
		}
		try {
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			LOGGER.error("释放连接异常", e);
		}
	}
}
