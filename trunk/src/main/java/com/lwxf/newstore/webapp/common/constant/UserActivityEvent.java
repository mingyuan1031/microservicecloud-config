package com.lwxf.newstore.webapp.common.constant;

/**
 * 功能：用户活动日志事件
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:57:22
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class UserActivityEvent {
	//正常登录
	public static final String login_normal = "login_normal";
	//异地登录
	public static final String login_remote = "login_remote";
	//非法登录被锁定(密码错误超过限定次数时被锁定)
	public static final String login_locked = "login_locked";
	//登录无效的密码
	public static final String login_invalid_password = "login_invalid_password";
	//重置密码请求
	public static final String restpass_req = "restpass_req";
	//重置密码
	public static final String resetpass = "resetpass";
	//修改头像
	public static final String md_avatar = "md_avatar";
	//修改名称
	public static final String md_name = "md_name";
	//修改手机
	public static final String md_mobile = "md_mobile";
	//修改生日
	public static final String md_birthday = "md_birthday";
	//修改时区
	public static final String md_time_zone = "md_time_zone";
	//修改信箱
	public static final String md_email = "md_email";
	//修改语言
	public static final String md_language = "md_language";
	//修改工作台背景图片
	public static final String md_background = "md_background";
	//修改用户登陆名
	public static final String md_username = "md_username";
}
