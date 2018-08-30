package com.lwxf.newstore.webapp.config.wxpay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.commons.lang3.StringUtils;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-27 19:06
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayConfiguration {
	@Autowired
	private WxPayProperties properties;

	@Bean
	@ConditionalOnMissingBean
	public WxPayConfig payConfig() {
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(this.properties.getAppId());
		payConfig.setMchId(this.properties.getMchId());
		payConfig.setMchKey(this.properties.getMchKey());
		payConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
		payConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
		payConfig.setKeyPath(this.properties.getKeyPath());
		payConfig.setNotifyUrl(this.properties.getNotifyUrl());

		return payConfig;
	}

	@Bean
	public WxPayService wxPayService(WxPayConfig payConfig) {
		WxPayService wxPayService = new WxPayServiceImpl();
		wxPayService.setConfig(payConfig);
		return wxPayService;
	}
}