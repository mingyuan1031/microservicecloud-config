package com.lwxf.newstore.webapp.common.jmail.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.mail.MailSendException;
import org.springframework.util.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.exception.InvalidEmailAddressException;
import com.lwxf.commons.exception.LwxfIOException;
import com.lwxf.commons.exception.UninitializedException;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.jmail.JMailService;
import com.lwxf.newstore.webapp.common.jmail.MailSend;
import com.lwxf.newstore.webapp.common.jmail.sendcloudmail.*;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.utils.ExceptionGenerateFactory;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:39
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SendCloudJMailServiceImpl implements JMailService {
	private static final Set<String> CUSTOMER_SERVICE_EMAIL_ADDRESS = new HashSet<String>() {
		private static final long serialVersionUID = -5313330743075747268L;

		{
			add("service@easypm.cn");
			/*add("d3shan@easypm.cn");*/
		}
	};
	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
		if (!(mailSend instanceof AbstractSendCloudMailSend)) {
			String message = "非法的MailSend对象，在这里只能是" + AbstractSendCloudMailSend.class.getName() + "的实现类";
			this.logger.error("######## " + message);
			throw new UninitializedException(message);
		}

		AbstractSendCloudMailSend _mailSend = (AbstractSendCloudMailSend) mailSend;
		SendCloudCfg sendCloudCfg = _mailSend.getSendCloudCfg();

		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(SendCloudCfg.SEND_TEMPLATE_URL);
		List<BasicNameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("template_invoke_name", sendCloudCfg.getEmailTemplate())); // 邮件模板名称

		nvps.add(new BasicNameValuePair("substitution_vars", _mailSend.getContent())); // json格式的替换变量
		nvps.add(new BasicNameValuePair("api_user", sendCloudCfg.getApiUser())); // 使用api_user和api_key进行验证
		nvps.add(new BasicNameValuePair("api_key", sendCloudCfg.getApiKey()));
		nvps.add(new BasicNameValuePair("from", sendCloudCfg.getFrom())); // 发信人，用正确邮件地址替代
		nvps.add(new BasicNameValuePair("fromname", sendCloudCfg.getFromname())); // 发信人别名
		nvps.add(new BasicNameValuePair("subject", _mailSend.getSubject(sender)));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			HttpResponse response = httpclient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
				String resultText = EntityUtils.toString(response.getEntity());
				Document result = DocumentHelper.parseText(resultText);
				Element message = (Element) result.selectSingleNode("/result/message");
				String messageTxt = message.getTextTrim();
				if (LwxfStringUtils.equals("error", messageTxt)) {
					Element error = (Element) result.selectSingleNode("/result/errors/error");
					resultText = "邮件发送失败：" + error.getTextTrim();
					LwxfIOException ioex = new LwxfIOException(resultText);
					/*if(resultText.indexOf("Request quota exceeded")>0){
						CustomerServicesUtils.sendJMSAndEMailWhileException(resultText,ioex);
					}*/
					this.logger.error(resultText.concat("，邮箱：").concat(mailSend.getTo().toString()));
					throw ioex;
				}
			} else {
				String errorInfo = "发送邮件时没有成功返回！\n\r" + "状态码：" + response.getStatusLine().getStatusCode();
				this.logger.error(errorInfo);
				throw new LwxfIOException(errorInfo);
			}
		} catch (MailSendException e) {
			this.logger.error("发送邮件异常", e);
			throw new InvalidEmailAddressException(e);
		} catch (UnsupportedEncodingException e) {
			this.logger.error("发送邮件异常", e);
			throw ExceptionGenerateFactory.createException(ErrorCodes.SYS_UNSUPPORT_ENCODING_00003, e);
		} catch (DocumentException | IOException e) {
			this.logger.error("发送邮件异常", e);
			throw ExceptionGenerateFactory.createException(ErrorCodes.SYS_IO_EXCEPTION_00007);
		}finally {
			httpPost.reset();
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
			case JMailService.MAILSEND_CLASSNAME_AUTHCODEMAIL:
				mailSend = new AuthCodeMail();
				break;
			case JMailService.MAILSEND_CLASSNAME_COMMONMAIL:
				mailSend = new CommonMail();
				break;
			case JMailService.MAILSEND_CLASSNAME_UPPASS:
				mailSend = new UpdatePasswordMail();
				break;
			case JMailService.MAILSEND_CLASSNAME_ONLINEMAIL:
				mailSend = new OnlineMail();
				break;
			default:
		}
		if (null == mailSend) {
			String message = mailSendSimpleCalssName + "在com.lwxf.newstore.domain.service.jmail.sendcloudmail包中未定义";
			logger.error("########## " + message);
			throw new UninitializedException(message);
		}
		return mailSend;
	}
}
