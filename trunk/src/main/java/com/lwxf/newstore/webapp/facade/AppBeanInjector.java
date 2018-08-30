package com.lwxf.newstore.webapp.facade;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;

import com.aliyuncs.IAcsClient;
import com.github.binarywang.wxpay.service.WxPayService;

import com.lwxf.commons.uniquekey.IdGererateFactory;
import com.lwxf.newstore.webapp.baseservice.cache.RedisUtils;
import com.lwxf.newstore.webapp.baseservice.cache.SyncGetByRedis;
import com.lwxf.newstore.webapp.baseservice.rabbitmq.RabbitMQSender;
import com.lwxf.newstore.webapp.baseservice.security.csrf.CsrfService;
import com.lwxf.newstore.webapp.bizservice.company.CompanyService;
import com.lwxf.newstore.webapp.bizservice.config.StoreConfigService;
import com.lwxf.newstore.webapp.bizservice.config.SystemConfigService;
import com.lwxf.newstore.webapp.bizservice.user.UserExtraService;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.bizservice.user.UserThirdInfoService;
import com.lwxf.newstore.webapp.common.component.BaseFileUploadComponent;
import com.lwxf.newstore.webapp.common.jmail.JMailService;
import com.lwxf.newstore.webapp.common.mobile.weixin.WeixinCfg;
import com.lwxf.newstore.webapp.common.mobile.weixin.service.IMsgService;
import com.lwxf.newstore.webapp.common.mobile.weixin.service.JsonMsgService;
import com.lwxf.newstore.webapp.common.uniquecode.UniquneCodeGenerator;
import com.lwxf.newstore.webapp.common.utils.Configuration;
import com.lwxf.newstore.webapp.common.utils.LocalMessageSourceUtil;
import com.lwxf.newstore.webapp.common.utils.SpringContextUtil;
import com.lwxf.newstore.webapp.config.RabbitMQProperties;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.mobile.WeixinFacade;
import com.lwxf.newstore.webapp.facade.user.UserFacade;
import com.lwxf.newstore.webapp.facade.user.UserThirdInfoFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:44:32
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class AppBeanInjector {
	/**
	 * 配置及工具注入
	 */
	public static final Configuration configuration;
	public static final LocalMessageSourceUtil i18nUtil;
	public static final IdGererateFactory idGererateFactory;
	// redis
	public static final RedisUtils redisUtils;
	public static final SyncGetByRedis syncGetByRedis;
	// 微信
	public static final WeixinCfg weixinCfg;
	public static final WxMpConfigStorage wxMpConfigStorage;
	public static final WxMpService wxMpService;
	public static final JsonMsgService weixinJsonMsgService;
	public static final WxPayService wxPayService;
	// 文件上传
	public static final BaseFileUploadComponent baseFileUploadComponent;
	public static final IMsgService weixinTemplateMsgService;

	// mq
	/*public static final RabbitMQSender rabbitMQSender;
	public static final RabbitMQProperties rabbitMQProperties;*/
	// 手机短信
	public static final IAcsClient smsService;
	// 编号生成器
	public static final UniquneCodeGenerator uniquneCodeGenerator;
	/**
	 * service 注入
	 */
	public static final JMailService jMailService;
	public static final UserService userService;
	public static final CsrfService csrfService;
	public static final UserExtraService userExtraService;
	public static final CompanyService companyService;
	public static final SystemConfigService systemConfigService;
	public static final StoreConfigService storeConfigService;
	public static final UserThirdInfoService userThirdInfoService;

	/**
	 * facade注入
	 */
	public static final UserFacade userFacade;
	public static final UserThirdInfoFacade userThirdInfoFacade;
	public static final WeixinFacade weixinFacade;


	static {
		// 工具及配置类
		configuration = (Configuration) SpringContextUtil.getBean("configuration");
		i18nUtil = (LocalMessageSourceUtil) SpringContextUtil.getBean("i18nUtil");
		idGererateFactory = (IdGererateFactory) SpringContextUtil.getBean("idGererateFactory");
		jMailService = (JMailService) SpringContextUtil.getBean("jMailService");
		weixinCfg = (WeixinCfg) SpringContextUtil.getBean("weixinCfg");
		wxMpService = (WxMpService) SpringContextUtil.getBean("wxMpService");
		wxMpConfigStorage = (WxMpConfigStorage)SpringContextUtil.getBean("wxMpConfigStorage");
		weixinJsonMsgService = (JsonMsgService) SpringContextUtil.getBean("weixinJsonMsgService");
		/*rabbitMQSender = (RabbitMQSender) SpringContextUtil.getBean("rabbitMQSender");
		rabbitMQProperties = (RabbitMQProperties) SpringContextUtil.getBean("rabbitMQProperties");*/
		smsService = (IAcsClient)SpringContextUtil.getBean("iAcsClientService");
		redisUtils = (RedisUtils)SpringContextUtil.getBean("redisUtils");
		syncGetByRedis = (SyncGetByRedis) SpringContextUtil.getBean("syncGetByRedis");
		baseFileUploadComponent = (BaseFileUploadComponent) SpringContextUtil.getBean("baseFileUploadComponent");
		uniquneCodeGenerator = (UniquneCodeGenerator) SpringContextUtil.getBean("uniquneCodeGenerator");
		weixinTemplateMsgService = (IMsgService) SpringContextUtil.getBean("weixinTemplateMsgService");
		wxPayService = (WxPayService) SpringContextUtil.getBean("wxPayService");

		// service bean
		userService = (UserService) SpringContextUtil.getBean("userService");
		csrfService = (CsrfService) SpringContextUtil.getBean("csrfService");
		userExtraService = (UserExtraService) SpringContextUtil.getBean("userExtraService");
		companyService = (CompanyService) SpringContextUtil.getBean("companyService");
		systemConfigService = (SystemConfigService) SpringContextUtil.getBean("systemConfigService");
		storeConfigService = (StoreConfigService) SpringContextUtil.getBean("storeConfigService");
		userThirdInfoService = (UserThirdInfoService) SpringContextUtil.getBean("userThirdInfoService");

		// facade bean
		userFacade = (UserFacade) SpringContextUtil.getBean("userFacade");
		userThirdInfoFacade = (UserThirdInfoFacade) SpringContextUtil.getBean("userThirdInfoFacade");
		weixinFacade = (WeixinFacade) SpringContextUtil.getBean("weixinFacade");
	}
}
