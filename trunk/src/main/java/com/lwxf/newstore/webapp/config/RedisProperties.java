package com.lwxf.newstore.webapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 功能：redis缓存配置文件
 *
 * @author：党冬(dangdong@ihydt.com)
 * @create：2017-03-21 8:40:47
 * @version：2017 Version：1.0
 * @company：创海科技 Created with IntelliJ IDEA
 */
@ConfigurationProperties(prefix = "lwxf.redis")
//@PropertySource("classpath:redis.properties")
@Component
public class RedisProperties {
	private int database = 0;
	private String host = "localhost";
	private String password;
	private String port = "6379";
	private Pool pool;
	private Sentinel sentinel;

	public RedisProperties() {
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Pool getPool() {
		return this.pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public int getDatabase() {
		return this.database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public Sentinel getSentinel() {
		return this.sentinel;
	}

	public void setSentinel(Sentinel sentinel) {
		this.sentinel = sentinel;
	}

	public static class Sentinel {
		private String master;
		private String nodes;

		public Sentinel() {
		}

		public String getMaster() {
			return this.master;
		}

		public void setMaster(String master) {
			this.master = master;
		}

		public String getNodes() {
			return this.nodes;
		}

		public void setNodes(String nodes) {
			this.nodes = nodes;
		}
	}

	public static class Pool {
		private int maxIdle = 8;
		private int minIdle = 0;
		private int maxActive = 8;
		private int maxWait = -1;

		public Pool() {
		}

		public int getMaxIdle() {
			return this.maxIdle;
		}

		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}

		public int getMinIdle() {
			return this.minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}

		public int getMaxActive() {
			return this.maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public int getMaxWait() {
			return this.maxWait;
		}

		public void setMaxWait(int maxWait) {
			this.maxWait = maxWait;
		}
	}
}
