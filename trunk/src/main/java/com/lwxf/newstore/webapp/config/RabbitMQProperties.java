package com.lwxf.newstore.webapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 功能：
 *		rabbitmq属性配置
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:29:21
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@ConfigurationProperties(prefix = "lwxf.rabbitmq")
//@PropertySource("classpath:rabbitmq.properties")
@Component
public class RabbitMQProperties {
	private String serverHost;
	private String port;
	private String username;
	private String password;
	private String virtualHost;
	private int heartbeat;
	private int connectionTimeout;

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	public int getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(int heartbeat) {
		this.heartbeat = heartbeat;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
}
