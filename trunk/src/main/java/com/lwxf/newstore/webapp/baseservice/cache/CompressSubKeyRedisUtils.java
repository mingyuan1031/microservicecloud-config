package com.lwxf.newstore.webapp.baseservice.cache;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import com.lwxf.newstore.webapp.baseservice.cache.RedisSubKeyCompressor.Key;
import com.lwxf.newstore.webapp.domain.entity.base.IdEntity;

/**
 * 功能：带Key压缩功能的 RedisUtils
 * Hash子key 使用存储对象中的 id 减去 主key中使用“:”分割后的最后一个值
 * 这里使用装饰模式，传入的redisUtils可能也是被装饰过的，所以没有被装饰的方法都直接调用redisUtils对应的方法，而没有调用super的方法
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 12:15:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("compressSubKeyRedisUtils")
public class CompressSubKeyRedisUtils extends RedisUtils {

	@Resource(name = "compressCacheNameRedisUtils")
	private CompressCacheNameRedisUtils redisUtils;

	@Resource(name = "redisSubKeyCompressor")
	private RedisKeyCompressor<Key, Key> redisKeyCompressor;

	private String compressSubKey(String key, String subKey) {
		return redisKeyCompressor.compress(new Key(key, Long.valueOf(subKey))).getCompressedSubKey();
		/*String[] keys = key.split(RedisConstant.SEPARATOR);
		long lSubKey = Long.parseLong(subKey);
		long lKey = Long.parseLong(keys[keys.length - 1]);
		return Long.toString(lSubKey - lKey);*/
	}

	private String unCompressSubKey(String key, String subKey) {
		return redisKeyCompressor.unCompress(new Key(key, subKey)).getSubKey().toString();
	}

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
		for (IdEntity idEntity : idEntities) {
			hPut(key, idEntity, expire);
		}
	}

	@Override
	public void hPut(String key, Iterable<? extends IdEntity> idEntities) {
		hPut(key, idEntities, -1);
	}

	@Override
	public void hPut(String key, String hashKey, Object value) {
		hPut(key, hashKey, value, -1);
	}

	@Override
	public void hPut(String key, String hashKey, Object value, long expire) {
		hPut(key, hashKey, value, expire, null);
	}

	@Override
	public void hPut(String key, String hashKey, Object value, long expire, TimeUnit timeUnit) {
		redisUtils.hPut(key, compressSubKey(key, hashKey), value, expire, timeUnit);
	}

	@Override
	public Object hGet(String key, String hashKey) {
		return redisUtils.hGet(key, compressSubKey(key, hashKey));
	}

	@Override
	public List hGet(String key, Collection<String> hashKeys) {
		List<String> keyList = hashKeys.stream().map(hashKey -> compressSubKey(key, hashKey)).collect(Collectors.toList());
		return redisUtils.hGet(key, keyList);
	}

	@Override
	public Boolean hExists(String key, String hashKey) {
		return redisUtils.hExists(key, compressSubKey(key, hashKey));
	}

	@Override
	public Long hDel(String key, String... hashKeys) {
		for (int i = 0, len = hashKeys.length; i < len; i++) {
			hashKeys[i] = compressSubKey(key, hashKeys[i]);
		}
		return redisUtils.hDel(key, hashKeys);
	}

	@Override
	public Set<String> hKeys(String key) {
		Set<String> keys = redisUtils.hKeys(key);
		Set<String> retKey = new HashSet<>();
		keys.forEach(subKey -> {
			retKey.add(unCompressSubKey(key, subKey));
		});
		return retKey;
	}

	@Override
	public List hGetAll(String key) {
		return redisUtils.hGetAll(key);
	}

	@Override
	public Long hLen(String key) {
		return redisUtils.hLen(key);
	}

	@Override
	public void sAdd(String key, Object[] values) {
		redisUtils.sAdd(key, values);
	}

	@Override
	public void sAdd(String key, Object[] values, long expire) {
		redisUtils.sAdd(key, values, expire);
	}

	@Override
	public void sAdd(String key, Object[] values, long expire, TimeUnit timeUnit) {
		redisUtils.sAdd(key, values, expire, timeUnit);
	}

	@Override
	public Long sCard(String key) {
		return redisUtils.sCard(key);
	}

	@Override
	public Set sMembers(String key) {
		return redisUtils.sMembers(key);
	}

	@Override
	public Long sRem(String key, Object... values) {
		return redisUtils.sRem(key, values);
	}

	@Override
	public Boolean sIsMember(String key, Object object) {
		return redisUtils.sIsMember(key, object);
	}

	@Override
	public Boolean zAdd(String key, Object value, double store) {
		return redisUtils.zAdd(key, value, store);
	}

	@Override
	public Boolean zAdd(String key, Object value, double store, long expire) {
		return redisUtils.zAdd(key, value, store, expire);
	}

	@Override
	public Long zAdd(String key, Set<TypedTuple<Object>> tuples) {
		return redisUtils.zAdd(key, tuples);
	}

	@Override
	public Boolean zAdd(String key, Object value, double store, long expire, TimeUnit timeUnit) {
		return redisUtils.zAdd(key, value, store, expire, timeUnit);
	}

	@Override
	public Long zCard(String key) {
		return redisUtils.zCard(key);
	}

	@Override
	public Long zCount(String key, double min, double max) {
		return redisUtils.zCount(key, min, max);
	}

	@Override
	public Double zIncrBy(String key, Object value, double delta) {
		return redisUtils.zIncrBy(key, value, delta);
	}

	@Override
	public Set zRange(String key, long start, long end) {
		return redisUtils.zRange(key, start, end);
	}

	@Override
	public Set zRangeByLex(String key, Range range) {
		return redisUtils.zRangeByLex(key, range);
	}

	@Override
	public Set zRangeByScore(String key, double min, double max) {
		return redisUtils.zRangeByScore(key, min, max);
	}

	@Override
	public Set zRangeByScore(String key, double min, double max, long offset, long count) {
		return redisUtils.zRangeByScore(key, min, max, offset, count);
	}

	@Override
	public Long zRank(String key, Object value) {
		return redisUtils.zRank(key, value);
	}

	@Override
	public Long zRem(String key, Object[] values) {
		return redisUtils.zRem(key, values);
	}

	@Override
	public Long zRemRangeByScore(String key, double min, double max) {
		return redisUtils.zRemRangeByScore(key, min, max);
	}

	@Override
	public Set zRevRangeByScore(String key, double min, double max) {
		return redisUtils.zRevRangeByScore(key, min, max);
	}

	@Override
	public Set zRevRangeByScore(String key, double min, double max, long offset, long count) {
		return redisUtils.zRevRangeByScore(key, min, max, offset, count);
	}

	@Override
	public void set(String key, Object value) {
		redisUtils.set(key, value);
	}

	@Override
	public void set(String key, Object value, long expire) {
		redisUtils.set(key, value, expire);
	}

	@Override
	public void set(String key, Object value, long expire, TimeUnit timeUnit) {
		redisUtils.set(key, value, expire, timeUnit);
	}

	@Override
	public Object get(String key) {
		return redisUtils.get(key);
	}

	@Override
	public Boolean setNX(String key, String value) {
		return redisUtils.setNX(key, value);
	}

	@Override
	public Boolean setNX(String key, String value, long expire) {
		return redisUtils.setNX(key, value, expire);
	}

	@Override
	public void del(String key) {
		redisUtils.del(key);
	}

	@Override
	public void del(Collection<String> keys) {
		redisUtils.del(keys);
	}


	@Override
	public Boolean isExist(String cacheKey) {
		return redisUtils.isExist(cacheKey);
	}
}
