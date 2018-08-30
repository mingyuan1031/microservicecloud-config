package com.lwxf.newstore.webapp.common.jmail.impl;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.exception.UninitializedException;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.jmail.JMailService;
import com.lwxf.newstore.webapp.common.jmail.MailSend;
import com.lwxf.newstore.webapp.common.jmail.mail.*;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.utils.ExceptionGenerateFactory;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:33
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class JMailServiceImpl implements JMailService {
	private static final Set<String> CUSTOMER_SERVICE_EMAIL_ADDRESS = new HashSet<String>() {
		private static final long serialVersionUID = -5313330743075747268L;

		{
			add("service@easypm.cn");
			/*add("d3shan@easypm.cn");*/
		}
	};
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private JavaMailSender mailSender;

	@Override
	public void send(MailSend mailSend) {
		this._send(mailSend, ShiroUtil.getCurrUser());
	}

	@Override
	public void send(MailSend mailSend, User sender) {
		Assert.notNull(sender, "邮件发送人sender不能为空");
		this._send(mailSend, sender);
	}

	@Override
	public void sendToCustomerService(MailSend mailSend) {
		mailSend.setTo(CUSTOMER_SERVICE_EMAIL_ADDRESS);
		User sender = new User();
		sender.setName("系统");
//		sender.setLoginName("system");
		sender.setEmail("system");
		sender.setId("");
		this._send(mailSend, sender);
	}

	private void _send(MailSend mailSend, User sender) {
		try {
			MimeMessage mime = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");
			String from = ((JavaMailSenderImpl) mailSender).getUsername();
			Assert.isTrue(LwxfStringUtils.isNotEmpty(from), "发送邮箱地址不能为空");

			helper.setFrom(from, mailSend.getPersonal());
			String[] tos = new String[mailSend.getTo().size()];
			helper.setTo(mailSend.getTo().toArray(tos));
			helper.setSubject(mailSend.getSubject(sender));
			String emailContent = mailSend.getContent();
			helper.setText(emailContent, true);
			mailSender.send(mime);
			logger.debug(LwxfStringUtils.format("发送给{0}的邮件已发出", mailSend.getTo().toString()));
		} catch (MailSendException | SendFailedException e) {
			this.logger.error("发送邮件异常", e);
			throw ExceptionGenerateFactory.createException(ErrorCodes.SYS_INVALID_EMAIL_ADDRESS_00009);
		} catch (MessagingException | UnsupportedEncodingException e) {
			this.logger.error("发送邮件异常", e);
		}
	}

	@Override
	public MailSend createMailSend(String mailSendSimpleCalssName) {
		MailSend mailSend = null;
		switch (mailSendSimpleCalssName) {
			case JMailService.MAILSEND_CLASSNAME_CLIENTIPINFOFAILMAIL:
				mailSend = new ClientIpInfoFailMail();
				break;
			case JMailService.MAILSEND_CLASSNAME_IMSENDEXCEPTIONMAIL:
				mailSend = new IMSendExceptionMail();
				break;
			case JMailService.MAILSEND_CLASSNAME_RESETPASSWORDMAIL:
				mailSend = new ResetPasswordMail();
				break;
			case JMailService.MAILSEND_CLASSNAME_SENDACTIVATIONMAIL:
				mailSend = new SendActivationMail();
				break;
			case JMailService.MAILSEND_CLASSNAME_SYSTEMEXCEPTIONMAIL:
				mailSend = new SystemExceptionMail();
				break;
			default:
		}
		if (null == mailSend) {
			String message = mailSendSimpleCalssName + "在com.lwxf.newstore.domain.service.jmail.mail包中未定义";
			logger.error("########## " + message);
			throw new UninitializedException(message);
		}
		return mailSend;
	}
}
