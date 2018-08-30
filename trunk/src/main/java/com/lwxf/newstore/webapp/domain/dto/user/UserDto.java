package com.lwxf.newstore.webapp.domain.dto.user;

/**
 * 功能：按一组用户id返回用户信息
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:49:32
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class UserDto {
	/**
	 * 用户标识
	 */
	private String id;
	/**
	 * 用户名称
	 */
	private String name;

	public UserDto() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
