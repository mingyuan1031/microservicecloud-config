package com.lwxf.newstore.webapp.common.jmail;

import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:30
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface JMailService {
	public static final String MAILSEND_CLASSNAME_CLIENTIPINFOFAILMAIL = "ClientIpInfoFailMail";
	public static final String MAILSEND_CLASSNAME_IMSENDEXCEPTIONMAIL = "IMSendExceptionMail";
	public static final String MAILSEND_CLASSNAME_INVITATIONMAIL = "InvitationMail";
	public static final String MAILSEND_CLASSNAME_RESETPASSWORDMAIL = "ResetPasswordMail";
	public static final String MAILSEND_CLASSNAME_SENDACTIVATIONMAIL = "SendActivationMail";
	public static final String MAILSEND_CLASSNAME_SYSTEMEXCEPTIONMAIL = "SystemExceptionMail";
	public static final String MAILSEND_CLASSNAME_AUTHCODEMAIL = "AuthCodeMail";
	public static final String MAILSEND_CLASSNAME_COMMONMAIL = "CommonMail";
	public static final String MAILSEND_CLASSNAME_UPPASS = "UpdatePasswordMail";
	public static final String MAILSEND_CLASSNAME_ONLINEMAIL = "OnlineMail";

	void send(MailSend mailSend);

	/**
	 * @param mailSend
	 * @param sender   : 发送邮件的用户(派发邮件的时候需要发送人信息时传入该参数)
	 *                 一般邮件主题或邮件内容会用到
	 */
	void send(MailSend mailSend, User sender);

	/**
	 * 给客服或系统邮箱发送邮件
	 *
	 * @param mailSend
	 */
	void sendToCustomerService(MailSend mailSend);

	MailSend createMailSend(String mailSendSimpleCalssName);
}
