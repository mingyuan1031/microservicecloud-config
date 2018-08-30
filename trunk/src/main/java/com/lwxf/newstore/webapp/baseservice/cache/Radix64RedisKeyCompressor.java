package com.lwxf.newstore.webapp.baseservice.cache;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.Radix64Util;
import com.lwxf.newstore.webapp.baseservice.cache.constant.RedisConstant;

/**
 * 功能：将10进制的key转换成64进制
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class Radix64RedisKeyCompressor implements RedisKeyCompressor<String,String> {

	@Override
	public String compress(String src) {
		return parse(src, true);
	}

	@Override
	public String unCompress(String dst) {
		return parse(dst, false);
	}

	private String parse(String key,boolean isCompress) {
		Assert.notNull(key, "key不能为null");
		String[] keys = key.split(RedisConstant.SEPARATOR);
		StringBuilder result;
		if (keys.length > 0) {
			result = new StringBuilder(keys[0]);
			for (int i = 1, len = keys.length; i < len; i++) {
				result.append(RedisConstant.SEPARATOR);
				String k = keys[i];
				if (LwxfStringUtils.isNumeric(k)) {
					if (isCompress) {
						result.append(Radix64Util.to64(Long.parseLong(k)));
					} else {
						result.append(Radix64Util.to10(k));
					}
				} else {
					result.append(k);
				}

			}
			return result.toString();
		}
		return key;
	}

}
