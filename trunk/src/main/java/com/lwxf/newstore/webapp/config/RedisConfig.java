package com.lwxf.newstore.webapp.config;

import redis.clients.jedis.JedisPoolConfig;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lwxf.commons.date.DateFormats;
import com.lwxf.commons.security.DesTool;
import com.lwxf.newstore.webapp.baseservice.cache.RedisTemplateDelegate;
import com.lwxf.newstore.webapp.baseservice.cache.SequenceGenerator;

/**
 * 功能：Redis配置
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-12 11:37:33
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class RedisConfig {
	@Autowired
	private RedisProperties redisProperties;
	private static final DesTool desTool = new DesTool();
	/**
	 * 注入 RedisConnectionFactory
	 */
	@Bean(name={"jedisConnectionFactory"})
	public RedisConnectionFactory jedisConnectionFactory(){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(redisProperties.getPool().getMaxActive());
		poolConfig.setMaxIdle(redisProperties.getPool().getMaxIdle());
		poolConfig.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnCreate(true);
		poolConfig.setTestWhileIdle(true);
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
		jedisConnectionFactory.setHostName(desTool.decrypt(redisProperties.getHost()));
		if(null != redisProperties.getPassword()){
			jedisConnectionFactory.setPassword(desTool.decrypt(redisProperties.getPassword()));
		}
		jedisConnectionFactory.setPort(Integer.parseInt(desTool.decrypt(redisProperties.getPort())));
		//其他配置，可再次扩展
		return jedisConnectionFactory;
	}
	/**
	 * 实例化 RedisTemplate 对象
	 *
	 * @return
	 */
	@Bean("redisTemplate")
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplateDelegate<>();
		initRedisTemplate(redisTemplate, jedisConnectionFactory(),true);
		return redisTemplate;
	}


	@Bean(name = "notSupportTransactionRedisTemplate")
	public RedisTemplate<String, Object> notSupportTransactionRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		initRedisTemplate(redisTemplate, jedisConnectionFactory(),false);
		return redisTemplate;
	}

	/**
	 * 设置数据存入 redis 手工增加配置项
	 *
	 * @param redisTemplate
	 * @param factory
	 */
	private void initRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory,boolean supportTransaction) {
		/**
		 * redis的序列化方式
		 */
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		/**
		 * 日期格式化默认使用 yyyy-MM-dd HH:mm:ss 跟MVC返回结果的格式保持一致
		 */
		om.setDateFormat(new SimpleDateFormat(DateFormats.STD_DATE_TIME));
		om.setSerializationInclusion(Include.NON_NULL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setConnectionFactory(factory);
		/**
		 * 开启事务支持
		 */
		redisTemplate.setEnableTransactionSupport(supportTransaction);
		redisTemplate.afterPropertiesSet();
	}

	/**
	 * 实例化 HashOperations 对象,可以使用 Hash 类型操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHash();
	}

	/**
	 * 实例化 ValueOperations 对象,可以使用 String 操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForValue();
	}

	/**
	 * 实例化 ListOperations 对象,可以使用 List 操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForList();
	}

	/**
	 * 实例化 SetOperations 对象,可以使用 Set 操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForSet();
	}

	/**
	 * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForZSet();
	}

	@Bean
	public SequenceGenerator sequenceGenerator(){
		return new SequenceGenerator();
	}



	/**
	 * 实例化 HashOperations 对象,可以使用 Hash 类型操作
	 *
	 * @param notSupportTransactionRedisTemplate
	 * @return
	 */
	@Bean
	public HashOperations<String, String, Object> hashOperationsNotSupportTransaction(RedisTemplate<String, Object> notSupportTransactionRedisTemplate) {
		return notSupportTransactionRedisTemplate.opsForHash();
	}

	/**
	 * 实例化 ValueOperations 对象,可以使用 String 操作
	 *
	 * @param notSupportTransactionRedisTemplate
	 * @return
	 */
	@Bean
	public ValueOperations<String, Object> valueOperationsNotSupportTransaction(RedisTemplate<String, Object> notSupportTransactionRedisTemplate) {
		return notSupportTransactionRedisTemplate.opsForValue();
	}

	/**
	 * 实例化 ListOperations 对象,可以使用 List 操作
	 *
	 * @param notSupportTransactionRedisTemplate
	 * @return
	 */
	@Bean
	public ListOperations<String, Object> listOperationsNotSupportTransaction(RedisTemplate<String, Object> notSupportTransactionRedisTemplate) {
		return notSupportTransactionRedisTemplate.opsForList();
	}

	/**
	 * 实例化 SetOperations 对象,可以使用 Set 操作
	 *
	 * @param notSupportTransactionRedisTemplate
	 * @return
	 */
	@Bean
	public SetOperations<String, Object> setOperationsNotSupportTransaction(RedisTemplate<String, Object> notSupportTransactionRedisTemplate) {
		return notSupportTransactionRedisTemplate.opsForSet();
	}

	/**
	 * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
	 *
	 * @param notSupportTransactionRedisTemplate
	 * @return
	 */
	@Bean
	public ZSetOperations<String, Object> zSetOperationsNotSupportTransaction(RedisTemplate<String, Object> notSupportTransactionRedisTemplate) {
		return notSupportTransactionRedisTemplate.opsForZSet();
	}
}
