package com.lwxf.newstore.webapp.common.jmail.mail;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 12:37
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class IMSendExceptionMail extends AbstractMailSend {
	@Override
	public String getSubject(User sender) {
		return "发送即时消息异常";
	}

	@Override
	public String getContent() {
		String message = "<p>发送即时消息时出现异常或错误，详细信息如下：</p><p>" + this.getMessage() + "</p>";
		return LwxfStringUtils.format(CONTENT_TEMPLATE, message);
	}
}
