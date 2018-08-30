package com.lwxf.newstore.webapp.domain.dto.user;

import java.util.Date;

/**
 * 功能：	jsonMapper.addFilterAllExclude(User.class, "loginName", "lastLogin", "timeZone", "metadata", "language");
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:49:32
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class UsersDto {
	/**
	 * 用户标识
	 */
	private String id;
	/**
	 * 用户名称
	 */
	private String name;
	private String email;
	private String avatar;
	private Date created;
	//	private Date lastLogin;
//	private String timeZone;
//	private String language;
//	private String metadata;
	private Date birthday;
	private String mobile;
//	private Byte state;

	public UsersDto() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
