package com.lwxf.newstore.webapp.baseservice.cache;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.lwxf.newstore.webapp.baseservice.cache.constant.RedisConstant;

/**
 * 功能：默认的 Redis key 生成器 通过模式（perfix:?:?）使用参数替换占位符
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("defaultRedisKeyGenerator")
public class DefaultRedisKeyGenerator<T> implements RedisKeyGenerator<T> {
	@Override
	public String generate(String prefix, T... params) {
		Assert.notNull(prefix, "参数不能为null");
		Assert.notEmpty(params, "参数不能为空");
		StringBuilder result = new StringBuilder(prefix);
		if (params != null) {
			for (int i = 0, len = params.length; i < len; i++) {
				result.append(params[i]).append(RedisConstant.SEPARATOR);
			}
		}
		return result.substring(0,result.length()-1);
	}
}
