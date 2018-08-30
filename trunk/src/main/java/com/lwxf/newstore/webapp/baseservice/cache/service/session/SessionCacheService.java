package com.lwxf.newstore.webapp.baseservice.cache.service.session;

/**
 * 功能：session基本新增，更新，删除
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface SessionCacheService {
	/**
	 * 保存session
	 *
	 * @param sessionId
	 * @param value
	 */
	void saveSession(String sessionId, byte[] value);

	/**
	 * 删除session
	 *
	 * @param sessionId
	 */
	void deleteBySessionId(String sessionId);

	/**
	 * 获取session
	 *
	 * @param sessionId
	 * @return
	 */
	byte[] findBySessionId(String sessionId);
}
