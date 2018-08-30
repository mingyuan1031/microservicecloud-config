package com.lwxf.newstore.webapp.common.jmail.mail;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:46
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ClientIpInfoFailMail extends AbstractMailSend {
	public ClientIpInfoFailMail() {
		this.message = "获取登录用户的客户端IP地址详细信息失败";
	}

	@Override
	public String getSubject(User sender) {
		return "获取客户端地址异常";
	}

	@Override
	public String getContent() {
		return LwxfStringUtils.format(CONTENT_TEMPLATE, "<p>" + this.message + "</p>");
	}
}
