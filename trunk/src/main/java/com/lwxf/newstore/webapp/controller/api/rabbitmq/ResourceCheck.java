package com.lwxf.newstore.webapp.controller.api.rabbitmq;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:40:10
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ResourceCheck extends BaseCheck{
	private String resource;
	private String name;
	private String permission;

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "ResourceCheck{" +
				"resource='" + resource + '\'' +
				", name='" + name + '\'' +
				", permission='" + permission + '\'' +
				'}';
	}
}
