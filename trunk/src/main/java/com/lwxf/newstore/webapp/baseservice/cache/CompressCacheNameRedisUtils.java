package com.lwxf.newstore.webapp.baseservice.cache;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.lwxf.newstore.webapp.domain.entity.base.IdEntity;

/**
 * 功能：Redis工具类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("compressCacheNameRedisUtils")
public class CompressCacheNameRedisUtils extends RedisUtils {
	@Resource(name = "radix64RedisKeyCompressor")
	protected RedisKeyCompressor<String, String> redisKeyCompressor;
	@Resource(name = "redisUtils")
	private RedisUtils redisUtils;

	public CompressCacheNameRedisUtils() {
	}

	@Override
	public Boolean isExist(String cacheKey) {
		cacheKey = redisKeyCompressor.compress(cacheKey);
		return redisUtils.isExist(cacheKey);
	}


	/**
	 * Hash操作
	 */

	@Override
	public void hPut(String key, String hashKey, Object value) {
		hPut(key, hashKey, value, -1);
	}

	/**
	 * 添加
	 *
	 * @param key     缓存key
	 * @param hashKey hash中的key
	 * @param value   hash中的value
	 * @param expire  过期时间(单位:天),传入 -1 时表示不设置过期时间
	 */
	@Override
	public void hPut(String key, String hashKey, Object value, long expire) {
		hPut(key, hashKey, value, expire, null);
	}

	@Override
	public void hPut(String key, String hashKey, Object value, long expire, TimeUnit timeUnit) {
		key = redisKeyCompressor.compress(key);
		redisUtils.hPut(key, hashKey, value, expire, timeUnit);
	}

	/**
	 * 使用idEntity 的id作为hashKey
	 *
	 * @param key
	 * @param idEntity
	 * @param expire
	 */
	@Override
	public <T extends IdEntity> void hPut(String key, T idEntity, long expire) {
		hPut(key, idEntity.getId(), idEntity, expire);
	}

	@Override
	public <T extends IdEntity> void hPut(String key, T idEntity) {
		hPut(key, idEntity, -1);
	}

	@Override
	public void hPut(String key, Iterable<? extends IdEntity> idEntities, long expire) {
		if (idEntities == null) {
			return;
		}
		for (IdEntity idEntity : idEntities) {
			hPut(key, idEntity, expire);
		}
	}

	@Override
	public void hPut(String key, Iterable<? extends IdEntity> idEntities) {
		hPut(key, idEntities, -1);
	}


	/**
	 * 获取一个对象
	 *
	 * @param key
	 * @param hashKey
	 * @return
	 */
	@Override
	public Object hGet(String key, String hashKey) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.hGet(key, hashKey);
	}

	/**
	 * 获取多个key
	 *
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	@Override
	public List hGet(String key, Collection<String> hashKeys) {
		if (CollectionUtils.isEmpty(hashKeys)) {
			return Collections.emptyList();
		}
		key = redisKeyCompressor.compress(key);
		return redisUtils.hGet(key, hashKeys);
	}

	/**
	 * 判断hash中是否存在hashKey
	 *
	 * @param key
	 * @param hashKey
	 * @return
	 */
	@Override
	public Boolean hExists(String key, String hashKey) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.hExists(key, hashKey);
	}

	@Override
	public Long hDel(String key, String... hashKeys) {
		if (hashKeys == null || hashKeys.length == 0) {
			return -1L;
		}
		key = redisKeyCompressor.compress(key);
		return redisUtils.hDel(key, hashKeys);
	}

	/**
	 * 得到Hash中的所有key
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Set<String> hKeys(String key) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.hKeys(key);
	}

	/**
	 * 得到key中的所有元素
	 *
	 * @param key
	 * @return
	 */
	@Override
	public List hGetAll(String key) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.hGetAll(key);
	}

	/**
	 * 得到Hash存放元素的数量
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Long hLen(String key) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.hLen(key);
	}

	/**
	 * Set操作
	 */

	@Override
	public void sAdd(String key, Object[] values) {
		sAdd(key, values, -1);
	}

	/**
	 * 向集合添加一个或多个成员
	 *
	 * @param key
	 * @param values
	 * @param expire
	 */
	@Override
	public void sAdd(String key, Object[] values, long expire) {
		sAdd(key, values, expire, null);
	}

	@Override
	public void sAdd(String key, Object[] values, long expire, TimeUnit timeUnit) {
		if (values == null || values.length == 0) {
			return;
		}
		key = redisKeyCompressor.compress(key);
		redisUtils.sAdd(key, values, expire, timeUnit);
	}

	/**
	 * 获取集合的成员数
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Long sCard(String key) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.sCard(key);
	}

	/**
	 * 返回集合中的所有成员
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Set sMembers(String key) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.sMembers(key);
	}

	/**
	 * 移除集合中一个或多个成员
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	@Override
	public Long sRem(String key, Object... values) {
		if (values == null || values.length == 0) {
			return -1L;
		}
		key = redisKeyCompressor.compress(key);
		return redisUtils.sRem(key, values);
	}

	/**
	 * 判断 member 元素是否是集合 key 的成员
	 *
	 * @param key
	 * @param object
	 * @return
	 */
	@Override
	public Boolean sIsMember(String key, Object object) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.sIsMember(key, object);
	}

	/**
	 * 操作Stored set
	 */

	@Override
	public Boolean zAdd(String key, Object value, double store) {
		return zAdd(key, value, store, -1);
	}

	/**
	 * 向有序集合添加一个或者更新已存在成员的分数
	 *
	 * @param key   键
	 * @param value 值
	 * @param store 分数
	 * @return 是否添加成功
	 */
	@Override
	public Boolean zAdd(String key, Object value, double store, long expire) {
		return zAdd(key, value, store, expire, null);
	}

	@Override
	public Boolean zAdd(String key, Object value, double store, long expire, TimeUnit timeUnit) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zAdd(key, value, store, expire, timeUnit);
	}

	/**
	 * 向有序集合添加多个成员，或者更新已存在成员的分数
	 *
	 * @param key
	 * @param tuples
	 * @return
	 */
	@Override
	public Long zAdd(String key, Set<TypedTuple<Object>> tuples) {
		if (CollectionUtils.isEmpty(tuples)) {
			return -1L;
		}
		key = redisKeyCompressor.compress(key);
		return redisUtils.zAdd(key, tuples);
	}

	/**
	 * 获取有序集合的成员数
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Long zCard(String key) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zCard(key);
	}

	/**
	 * 计算在有序集合中指定区间分数的成员数
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	@Override
	public Long zCount(String key, double min, double max) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zCount(key, min, max);
	}

	/**
	 * 有序集合中对指定成员的分数加上增量 increment
	 *
	 * @param key
	 * @param value 要增加分数的成员
	 * @param delta 增量
	 * @return
	 */
	@Override
	public Double zIncrBy(String key, Object value, double delta) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zIncrBy(key, value, delta);
	}

	/**
	 * 通过索引区间返回有序集合成指定区间内的成员
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	@Override
	public Set zRange(String key, long start, long end) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zRange(key, start, end);
	}

	/**
	 * 通过字典区间返回有序集合的成员
	 *
	 * @param key
	 * @param range
	 * @return
	 */
	@Override
	public Set zRangeByLex(String key, Range range) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zRangeByLex(key, range);
	}

	/**
	 * 通过分数返回有序集合指定区间内的成员
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	@Override
	public Set zRangeByScore(String key, double min, double max) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zRangeByScore(key, min, max);
	}

	/**
	 * 返回有序集合中指定分数区间的成员列表。有序集成员按分数值递增(从小到大)次序排列。
	 * 默认情况下，区间的取值使用闭区间 (小于等于或大于等于)
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @param offset 从哪一条开始（不包含这一条）
	 * @param count  取多少条
	 * @return
	 */
	@Override
	public Set zRangeByScore(String key, double min, double max, long offset, long count) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zRangeByScore(key, min, max, offset, count);
	}

	/**
	 * 返回有序集合中指定成员的索引
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Long zRank(String key, Object value) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zRank(key, value);
	}

	/**
	 * 移除有序集合中的一个或多个成员
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	@Override
	public Long zRem(String key, Object[] values) {
		if (values == null || values.length == 0) {
			return -1L;
		}
		key = redisKeyCompressor.compress(key);
		return redisUtils.zRem(key, values);
	}


	/**
	 * 移除有序集合中给定的分数区间的所有成员
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	@Override
	public Long zRemRangeByScore(String key, double min, double max) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zRemRangeByScore(key, min, max);
	}

	/**
	 * 返回有序集中指定分数区间内的成员，分数从高到低排序
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	@Override
	public Set zRevRangeByScore(String key, double min, double max) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zRevRangeByScore(key, min, max);
	}

	/**
	 * 返回有序集中指定分数区间内的从offset开始的count个成员，分数从高到低排序
	 *
	 * @param key
	 * @param min
	 * @param max
	 * @param offset 从哪一条开始（不包含这一条）
	 * @param count  去多少条
	 * @return
	 */
	@Override
	public Set zRevRangeByScore(String key, double min, double max, long offset, long count) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.zRevRangeByScore(key, min, max, offset, count);
	}

	/**
	 * String 操作
	 */

	/**
	 * 设置指定 key 的值
	 *
	 * @param key
	 * @param value
	 */
	@Override
	public void set(String key, Object value) {
		set(key, value, -1);
	}

	/**
	 * 获取指定 key 的值带过期时间
	 *
	 * @param key
	 * @param value
	 * @param expire
	 */
	@Override
	public void set(String key, Object value, long expire) {
		set(key, value, expire, null);
	}

	@Override
	public void set(String key, Object value, long expire, TimeUnit timeUnit) {
		key = redisKeyCompressor.compress(key);
		redisUtils.set(key, value, expire, timeUnit);
	}

	/**
	 * 获取指定 key 的值。
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Object get(String key) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.get(key);
	}

	/**
	 * 只有在 key 不存在时设置 key 的值。
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Boolean setNX(String key, String value) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.setNX(key, value);
	}

	/**
	 * 只有在 key 不存在时设置 key 的值。带过期时间
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Boolean setNX(String key, String value, long expire) {
		key = redisKeyCompressor.compress(key);
		return redisUtils.setNX(key, value);
	}

	@Override
	public void del(String key) {
		key = redisKeyCompressor.compress(key);
		redisUtils.del(key);
	}

	@Override
	public void del(Collection<String> keys) {
		List<String> keyList = new ArrayList<>();
		keys.forEach(key -> {
			keyList.add(redisKeyCompressor.compress(key));
		});
		redisUtils.del(keyList);
	}

}
