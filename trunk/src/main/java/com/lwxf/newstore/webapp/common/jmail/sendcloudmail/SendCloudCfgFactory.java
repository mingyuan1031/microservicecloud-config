package com.lwxf.newstore.webapp.common.jmail.sendcloudmail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.lwxf.commons.security.DesTool;
import com.lwxf.newstore.webapp.common.jmail.MailSend;
import com.github.pagehelper.util.StringUtil;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:50
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SendCloudCfgFactory {
	protected static final Logger logger = LoggerFactory.getLogger(SendCloudCfgFactory.class);
	// 发送邮件的相关配置
	public static String EMAIL_FROM;
	public static String EMAIL_FROMNAME;
	public static String SEND_CLOUD_API_KEY;
	protected static String SEND_CLOUD_USER_TRAN;
	public static String SEND_CLOUD_USER_BATCH;
	protected static String SEND_CLOUD_TEMPLATE_REGISTER;
	protected static String SEND_CLOUD_TEMPLATE_RESETPASS;
	protected static String SEND_CLOUD_TEMPLATE_INVITATION;
	protected static String SEND_CLOUD_TEMPLATE_SYSTEM;
	public static String c_send_cloud_group_mail_list_addr;
	public static String c_send_cloud_template_update;
	// 修改密码的通知模版
	public static String c_send_cloud_template_uppass;
	//新版上线的邮件模版
	public static String SEND_CLOUD_TEMPLATE_ONLINE;
	public static final String SEND_CLOUD_TEMPLATE_COMMON="easypm-common";
	/*<p>d5shan，您好：</p>

	<p>您于 2015-08-19 14:27:02 申请重置密码，请点击下方链接或拷贝至浏览器地址栏进行密码重置</p>*/

	public static final void setCfg(String cfg) {
		Assert.isTrue(StringUtil.isNotEmpty(cfg),"配置不能为空");
		DesTool desTool = new DesTool();
		String[] cfgArr = desTool.decrypt(cfg).split("=");
		Assert.isTrue(cfgArr.length == 5, "Send Cloud 配置项不全");
		EMAIL_FROM = cfgArr[0];
		EMAIL_FROMNAME = cfgArr[1];
		SEND_CLOUD_API_KEY = cfgArr[2];
		SEND_CLOUD_USER_TRAN = cfgArr[3];
		SEND_CLOUD_USER_BATCH = cfgArr[4];
	}

	public static final void setMailTemplate(String tmp) {
		Assert.isTrue(StringUtil.isNotEmpty(tmp),"模版配置不能为空");
		String[] tmpArr = tmp.split("=");
		Assert.isTrue(tmpArr.length == 8, "Send Cloud 模版配置不正确");

		SEND_CLOUD_TEMPLATE_REGISTER = tmpArr[0];
		SEND_CLOUD_TEMPLATE_RESETPASS = tmpArr[1];
		SEND_CLOUD_TEMPLATE_INVITATION = tmpArr[2];
		SEND_CLOUD_TEMPLATE_SYSTEM = tmpArr[3];
		c_send_cloud_group_mail_list_addr = tmpArr[4];
		c_send_cloud_template_update = tmpArr[5];
		c_send_cloud_template_uppass = tmpArr[6];
		SEND_CLOUD_TEMPLATE_ONLINE = tmpArr[7];
	}

	public static SendCloudCfg createSendCloudCfg(MailSend mailSend) {
		SendCloudCfg cfg = null;
		if (mailSend instanceof ClientIpInfoFailMail) {
			cfg = new ClientIpInfoFailMailCfg();
		} else if (mailSend instanceof IMSendExceptionMail) {
			cfg = new IMSendExceptionMailCfg();
		} else if (mailSend instanceof SendActivationMail) {
			cfg = new SendActivationMailCfg();
		} else if (mailSend instanceof ResetPasswordMail) {
			cfg = new ResetPasswordMailCfg();
		} else if (mailSend instanceof SystemExceptionMail) {
			cfg = new SystemExceptionMailCfg();
		} else if (mailSend instanceof AuthCodeMail) {
			cfg = new AuthCodeMailCfg();
		}else if (mailSend instanceof CommonMail) {
			cfg = new CommonMailCfg();
		}else if (mailSend instanceof UpdatePasswordMail) {
			cfg = new UpdatePasswordMailCfg();
		}else if (mailSend instanceof OnlineMail) {
			cfg = new OnlineMailCfg();
		}
		if (null == cfg) {
			String message = mailSend.getClass().getName() + "在SendCloudCfgFactory中未定义";
			logger.debug("########## " + message);
			Assert.isTrue(false,message);
		}
		return cfg;
	}
}

abstract class AbstractSendCloudCfg implements SendCloudCfg {
	@Override
	public String getApiUser() {
		return SendCloudCfgFactory.SEND_CLOUD_USER_TRAN;
	}

	@Override
	public String getApiKey() {
		return SendCloudCfgFactory.SEND_CLOUD_API_KEY;
	}

	@Override
	public String getFrom() {
		return SendCloudCfgFactory.EMAIL_FROM;
	}

	@Override
	public String getFromname() {
		return SendCloudCfgFactory.EMAIL_FROMNAME;
	}

	@Override
	public String getEmailTemplate() {
		return SendCloudCfgFactory.SEND_CLOUD_TEMPLATE_SYSTEM;
	}
}

class ClientIpInfoFailMailCfg extends AbstractSendCloudCfg {
}

class IMSendExceptionMailCfg extends AbstractSendCloudCfg {
}

class SystemExceptionMailCfg extends AbstractSendCloudCfg {
}

class ResetPasswordMailCfg extends AbstractSendCloudCfg {
	@Override
	public String getEmailTemplate() {
		return SendCloudCfgFactory.SEND_CLOUD_TEMPLATE_RESETPASS;
	}
}

class SendActivationMailCfg extends AbstractSendCloudCfg {
	@Override
	public String getEmailTemplate() {
		return SendCloudCfgFactory.SEND_CLOUD_TEMPLATE_REGISTER;
	}
}

class AuthCodeMailCfg extends AbstractSendCloudCfg{
	@Override
	public String getEmailTemplate() {
		return SendCloudCfgFactory.SEND_CLOUD_TEMPLATE_COMMON;
	}
}

class CommonMailCfg extends AbstractSendCloudCfg{
	@Override
	public String getEmailTemplate() {
		return SendCloudCfgFactory.SEND_CLOUD_TEMPLATE_COMMON;
	}
}

class UpdatePasswordMailCfg extends AbstractSendCloudCfg{
	@Override
	public String getEmailTemplate() {
		return SendCloudCfgFactory.c_send_cloud_template_uppass;
	}
}

class OnlineMailCfg extends AbstractSendCloudCfg{
	@Override
	public String getEmailTemplate() {
		return SendCloudCfgFactory.SEND_CLOUD_TEMPLATE_ONLINE;
	}
}
