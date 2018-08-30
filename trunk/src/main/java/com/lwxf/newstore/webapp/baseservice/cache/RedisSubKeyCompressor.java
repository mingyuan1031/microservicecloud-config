package com.lwxf.newstore.webapp.baseservice.cache;

import org.springframework.stereotype.Component;

import org.apache.shiro.util.StringUtils;

import com.lwxf.newstore.webapp.baseservice.cache.RedisSubKeyCompressor.Key;
import com.lwxf.newstore.webapp.baseservice.cache.constant.RedisConstant;

/**
 * 功能：压缩子Key
 * Hash子key 使用存储对象中的 id 减去 主key中使用“:”分割后的最后一个值
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class RedisSubKeyCompressor implements RedisKeyCompressor<Key, Key> {
	@Override
	public Key compress(Key key) {
		Long lKey = parseParentKey(key);
		key.setCompressedSubKey(key.getSubKey() - lKey + "");
		return key;
	}

	@Override
	public Key unCompress(Key key) {
		Long lKey = parseParentKey(key);
		if (key.getCompressedSubKey() != null) {
			key.setSubKey(Long.parseLong(key.getCompressedSubKey()) - lKey);
		}
		return key;
	}

	/**
	 * 使用 ":" 分割 parentKey，取得分割后的最后一个值
	 * @param key
	 * @return
	 */
	private Long parseParentKey(Key key) {
		if (key != null && StringUtils.hasText(key.getParentKey())) {
			String[] keys = key.getParentKey().split(RedisConstant.SEPARATOR);
			if (keys.length > 1) {
				try {
					return Long.valueOf(keys[keys.length - 1]);
				} catch (Exception e) {
					return 0L;
				}
			}
		}
		return 0L;
	}

	/**
	 * 封装 Key的结构
	 */
	static class Key {
		private String parentKey;
		private Long subKey;
		private String compressedSubKey;

		public Key(String parentKey, Long subKey) {
			this.parentKey = parentKey;
			this.subKey = subKey;
		}

		public Key(String parentKey, String compressedSubKey) {
			this.parentKey = parentKey;
			this.compressedSubKey = compressedSubKey;
		}


		public String getParentKey() {
			return parentKey;
		}

		public void setParentKey(String parentKey) {
			this.parentKey = parentKey;
		}

		public Long getSubKey() {
			return subKey;
		}

		public void setSubKey(Long subKey) {
			this.subKey = subKey;
		}

		public String getCompressedSubKey() {
			return compressedSubKey;
		}

		public void setCompressedSubKey(String compressedSubKey) {
			this.compressedSubKey = compressedSubKey;
		}
	}
}
