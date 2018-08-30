package com.lwxf.newstore.webapp.baseservice.cache;

import javax.annotation.Resource;

import java.util.List;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class RedisTemplateDelegate<K, V> extends RedisTemplate<K, V> {
	@Resource(name = "notSupportTransactionRedisTemplate")
	private RedisTemplate<K, V> notSupportTransactionRedisTemplate;

	@Override
	public <T> T execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline) {
		//判断是否有@Transactional注解，如果有就用支持事务的RedisTemplate
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			return super.execute(action, exposeConnection, pipeline);
		} else {
			return notSupportTransactionRedisTemplate.execute(action, exposeConnection, pipeline);
		}
	}

	@Override
	public <T> T execute(SessionCallback<T> session) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			return super.execute(session);
		} else {
			return notSupportTransactionRedisTemplate.execute(session);
		}
	}

	@Override
	public List<Object> executePipelined(final SessionCallback<?> session, final RedisSerializer<?> resultSerializer) {
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			return super.executePipelined(session, resultSerializer);
		} else {
			return notSupportTransactionRedisTemplate.executePipelined(session, resultSerializer);
		}
	}
}
