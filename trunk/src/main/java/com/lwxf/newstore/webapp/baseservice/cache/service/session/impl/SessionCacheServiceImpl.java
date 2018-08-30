package com.lwxf.newstore.webapp.baseservice.cache.service.session.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.lwxf.newstore.webapp.baseservice.cache.RedisUtils;
import com.lwxf.newstore.webapp.baseservice.cache.constant.RedisConstant;
import com.lwxf.newstore.webapp.baseservice.cache.service.session.SessionCacheService;

/**
 * 功能：session基本新增，更新，删除
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("sessionCacheService")
public class SessionCacheServiceImpl implements SessionCacheService {
	@Resource(name = "redisUtils")
	protected RedisUtils redisUtils;

	@Override
	public void saveSession(String sessionId, byte[] value) {
		String key = RedisConstant.EASYPM_SESSION + sessionId;
		redisUtils.set(key, value, RedisConstant.SESSION_TIMEOUT);
	}

	@Override
	public void deleteBySessionId(String sessionId) {
		String key = RedisConstant.EASYPM_SESSION + sessionId;
		redisUtils.del(key);
	}

	@Override
	public byte[] findBySessionId(String sessionId) {
		String key = RedisConstant.EASYPM_SESSION + sessionId;
		return (byte[]) redisUtils.get(key);
	}
}
