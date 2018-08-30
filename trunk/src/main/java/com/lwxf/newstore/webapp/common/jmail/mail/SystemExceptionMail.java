package com.lwxf.newstore.webapp.common.jmail.mail;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 18:39
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SystemExceptionMail extends AbstractMailSend {
	@Override
	public String getSubject(User sender) {
		return "系统出现异常";
	}

	@Override
	public String getContent() {
		return LwxfStringUtils.format(CONTENT_TEMPLATE, "<p>" + this.message + "</p>");
	}
}
