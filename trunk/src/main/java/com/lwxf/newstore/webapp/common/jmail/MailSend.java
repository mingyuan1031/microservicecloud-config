package com.lwxf.newstore.webapp.common.jmail;

import java.util.Set;

import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：邮件接口供外部实现自定邮件
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:32
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface MailSend {
	String getPersonal(); // 获取from的个性化名字

	String getMessage(); // 邮件附加信息

	void setMessage(String message);

	String getSubject(User sender); // 获取邮件主题；

	String getContent(); // 获取邮件内容；

	Set<String> getTo();

	void setTo(Set<String> tos); // 发送目标
}
