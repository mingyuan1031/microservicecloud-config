package com.lwxf.newstore.webapp.baseservice.cache;

/**
 * 功能：Redis中key的压缩
 * K 原始key，R 压缩后的结果
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface RedisKeyCompressor<K,R> {

	/**
	 * 压缩
	 * @param k
	 * @return
	 */
	R compress(K k);

	/**
	 * 解压
	 * @param r
	 * @return
	 */
	K unCompress(R r);
}
