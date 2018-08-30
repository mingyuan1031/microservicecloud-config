package com.lwxf.newstore.webapp.common.dto;

import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-12 9:32
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class UserInfoObj {
	private User user;
	private UserExtra userExtra;
	private UserThirdInfo userThirdInfo;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserExtra getUserExtra() {
		return userExtra;
	}

	public void setUserExtra(UserExtra userExtra) {
		this.userExtra = userExtra;
	}

	public UserThirdInfo getUserThirdInfo() {
		return userThirdInfo;
	}

	public void setUserThirdInfo(UserThirdInfo userThirdInfo) {
		this.userThirdInfo = userThirdInfo;
	}

	public static UserInfoObj newOne(User user,UserExtra userExtra,UserThirdInfo userThirdInfo){
		UserInfoObj obj = new UserInfoObj();
		obj.setUser(user);
		obj.setUserExtra(userExtra);
		obj.setUserThirdInfo(userThirdInfo);
		return obj;
	}
}
