package com.lwxf.newstore.webapp.baseservice.cache;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;

/**
 * 功能：在redis事务完成后释放锁
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class RedisLockTransactionSynchronizationAdapter extends TransactionSynchronizationAdapter {
	private String key;
	private RedisLockHandler redisLockHandler;

	public RedisLockTransactionSynchronizationAdapter(String key, RedisLockHandler redisLockHandler) {
		this.key = key;
		this.redisLockHandler = redisLockHandler;
	}

	@Override
	public void afterCompletion(int status) {
		redisLockHandler.unLock(key);
	}
}
