package com.lwxf.newstore.webapp.baseservice.cache;

/**
 * 功能：生成缓存的key
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface RedisKeyGenerator<T> {
	String generate(String cacheName, T... params);
}
