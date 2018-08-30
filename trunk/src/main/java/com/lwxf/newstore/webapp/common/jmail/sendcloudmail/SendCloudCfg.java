package com.lwxf.newstore.webapp.common.jmail.sendcloudmail;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:48
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface SendCloudCfg {
	public static final String SEND_TEMPLATE_URL = "https://sendcloud.sohu.com/webapi/mail.send_template.xml";

	String getApiKey();

	String getApiUser();

	String getFrom();

	String getFromname();

	String getEmailTemplate();
}
