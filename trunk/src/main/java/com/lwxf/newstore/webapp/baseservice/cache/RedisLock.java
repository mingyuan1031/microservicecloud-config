package com.lwxf.newstore.webapp.baseservice.cache;

import javax.annotation.Resource;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("redisLock")
public class RedisLock {
	private static final Long DEFAULT_TIME_OUT = 30L;

	@Resource(name = "redisLockHandlerImpl")
	protected RedisLockHandler redisLockHandler;

	protected ThreadLocal<LinkedList<String>> lockedKeyListThreadLocal = new ThreadLocal<>();

	/**
	 * 带锁的redis操作，防止并发时出现数据库数据与缓存数据不一致
	 *
	 * @param key          需要加锁的key
	 * @param lockCallback redis操作回调
	 */
	public void lock(String key, LockCallback lockCallback) {
		//允许锁重入
		LinkedList<String> keyList = lockedKeyListThreadLocal.get();
		if (keyList == null) {
			keyList = new LinkedList<>();
			lockedKeyListThreadLocal.set(keyList);
		}
		if (keyList.contains(key)) {
			lockCallback.doInLock();
			return;
		}
		keyList.add(key);

		if (redisLockHandler.tryLock(key, DEFAULT_TIME_OUT, TimeUnit.SECONDS)) {
			try {
				lockCallback.doInLock();
			} finally {
				releaseLock(key);
			}
		}
	}

	private void releaseLock(String key) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			//注册一个回调，在redis事务提交后释放锁，必须放在redis操作之后
			TransactionSynchronizationManager.
					registerSynchronization(new RedisLockTransactionSynchronizationAdapter(key, redisLockHandler));
			TransactionSynchronizationManager.
					registerSynchronization(new ClearLockedKeyAdapter());
		} else {
			LinkedList<String> keyList = lockedKeyListThreadLocal.get();
			if (keyList.isEmpty()) {
				lockedKeyListThreadLocal.remove();
			}else{
				redisLockHandler.unLock(keyList.removeLast());
				if (keyList.isEmpty()) {
					lockedKeyListThreadLocal.remove();
				}
			}
		}
	}

	public interface LockCallback {
		void doInLock();
	}

	private class ClearLockedKeyAdapter extends TransactionSynchronizationAdapter {
		@Override
		public void afterCompletion(int status) {
			LinkedList<String> keyList = lockedKeyListThreadLocal.get();
			if (keyList.isEmpty()) {
				lockedKeyListThreadLocal.remove();
			} else {
				keyList.removeLast();
				if (keyList.isEmpty()) {
					lockedKeyListThreadLocal.remove();
				}
			}
		}
	}
}
