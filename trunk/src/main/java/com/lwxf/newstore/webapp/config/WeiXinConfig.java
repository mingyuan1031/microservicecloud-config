package com.lwxf.newstore.webapp.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lwxf.newstore.webapp.common.mobile.weixin.WeixinCfg;

/**
 * 功能：微信相关bean
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:08:08
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class WeiXinConfig {

	private Logger logger = LoggerFactory.getLogger(WeiXinConfig.class);

	@Bean
	public WxMpService wxMpService(WxMpConfigStorage wxMpConfigStorage){
		WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
		return wxMpService;
	}

	@Bean
	public WxMpConfigStorage wxMpConfigStorage(WeixinCfg WeixinCfg){
		WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpInMemoryConfigStorage.setAppId(WeixinCfg.getAppId());
		wxMpInMemoryConfigStorage.setSecret(WeixinCfg.getSecret());
		wxMpInMemoryConfigStorage.setToken(WeixinCfg.getToken());
		return wxMpInMemoryConfigStorage;
	}
}
