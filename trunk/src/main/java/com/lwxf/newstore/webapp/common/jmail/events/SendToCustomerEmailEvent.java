package com.lwxf.newstore.webapp.common.jmail.events;

import org.springframework.context.ApplicationEvent;

import com.lwxf.newstore.webapp.common.jmail.MailSend;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:37
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public final class SendToCustomerEmailEvent extends ApplicationEvent {
	private static final long serialVersionUID = -7899823158892890418L;

	public SendToCustomerEmailEvent(MailSend source) {
		super(source);
	}
}
