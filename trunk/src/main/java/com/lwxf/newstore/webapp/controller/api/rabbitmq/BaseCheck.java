package com.lwxf.newstore.webapp.controller.api.rabbitmq;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:39:39
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class BaseCheck {
	private String username;

	private String vhost;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVhost() {
		return vhost;
	}

	public void setVhost(String vhost) {
		this.vhost = vhost;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" +
				"username='" + username + '\'' +
				", vhost='" + vhost + '\'' +
				'}';
	}
}
