package com.lwxf.newstore.webapp.common.jmail.listeners;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.lwxf.newstore.webapp.common.jmail.MailSend;
import com.lwxf.newstore.webapp.common.jmail.events.SendToCustomerEmailEvent;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：发送给客服邮箱或系统邮箱的listener
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:40
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class SendToCustomerEmailListener implements SmartApplicationListener {
	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
		return ClassUtils.isAssignable(eventType, SendToCustomerEmailEvent.class);
	}

	@Override
	public boolean supportsSourceType(Class<?> sourceType) {
		return ClassUtils.isAssignable(sourceType, MailSend.class);
	}
	@Async
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		AppBeanInjector.jMailService.sendToCustomerService((MailSend) event.getSource());
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
