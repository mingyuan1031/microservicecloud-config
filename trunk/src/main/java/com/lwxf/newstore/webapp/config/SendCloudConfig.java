package com.lwxf.newstore.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lwxf.newstore.webapp.common.jmail.JMailService;
import com.lwxf.newstore.webapp.common.jmail.impl.SendCloudJMailServiceImpl;
import com.lwxf.newstore.webapp.common.jmail.sendcloudmail.SendCloudCfgFactory;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 13:22:42
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class SendCloudConfig {
	@Value("${sendcloud.conn}")
	private String sendcloudConn;
	@Value("${sendcloud.templates}")
	private String sendcloudTemplates;

	@Bean
	public JMailService jMailService(){
		SendCloudCfgFactory.setCfg(this.sendcloudConn);
		SendCloudCfgFactory.setMailTemplate(this.sendcloudTemplates);
		return new SendCloudJMailServiceImpl();
	}
}
