package com.lwxf.newstore.webapp.baseservice.cache;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.lwxf.commons.exception.RedisLockTimeOutException;
import com.lwxf.commons.utils.IGetter;
import com.lwxf.newstore.webapp.baseservice.cache.constant.RedisConstant;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isSynchronizationActive;

/**
 * 功能：Redis实现同步锁
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("syncGetByRedis")
public final class SyncGetByRedis {

	@Resource(name = "redisLockHandlerImpl")
	protected RedisLockHandler redisLockHandler;

	/**
	 * Redis实现的锁 处理同一方法争同一资源
	 *
	 * @param syncKey 键
	 * @param getter  回调方法
	 * @param <T>
	 * @return
	 */
	public <T> T exec(String syncKey, IGetter<T> getter) {
		T value = null;
		if (redisLockHandler.tryLock(syncKey, RedisConstant.DEFAULT_LOCK_TIME_OUT, TimeUnit.MILLISECONDS)) {
			Long startTime = System.currentTimeMillis();
			try {
				value = getter.get();
				Long endTime = System.currentTimeMillis();
				long tmp = endTime - startTime;
				//锁住后方法执行超过锁默认释放时长，抛异常
				if (tmp > RedisConstant.DEFAULT_SINGLE_EXPIRE_TIME) {
					throw new RedisLockTimeOutException("Lock method execution time out Exception");
				}
			} catch (RuntimeException e) {
				throw e;
			} finally {
				//如果事务没有激活，则直接在方法执行完释放锁
				if (!isSynchronizationActive()) {
					redisLockHandler.unLock(syncKey);
				} else {
					//注册一个回调，在redis事务提交后释放锁，必须放在redis操作之后
					TransactionSynchronizationManager.
							registerSynchronization(new RedisLockTransactionSynchronizationAdapter(syncKey, redisLockHandler));
				}
			}

		} else {
			//获取锁超时,给调用者抛出获取锁超时异常
			throw new RedisLockTimeOutException();
		}
		return value;
	}
}