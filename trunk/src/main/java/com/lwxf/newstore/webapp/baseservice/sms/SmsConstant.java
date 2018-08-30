package com.lwxf.newstore.webapp.baseservice.sms;

/**
 * 功能：手机短信消息服务配置项
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:07:31
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SmsConstant {
	/**
	 * 短信验证码长度
	 */
	public static final int VERIFICATION_CODE_LENGTH = 4;
	/**
	 * 短信验证码过期时间
	 */
	public static final int VERIFICATION_CODE_EXPIRED = 5;
	/**
	 * 中文语言
	 */
	public static final String ZH_LANGUAGE = "zh";
	/**
	 * 产品名称:云通信短信API产品,开发者无需替换
	 */
	public static final String PRODUCT = "Dysmsapi";
	/**
	 * 阿里产品域名,开发者无需替换
	 */
	public static final String DOMAIN = "dysmsapi.aliyuncs.com";
	/**
	 * 区域
	 */
	public static final String REGION_ID = "cn-hangzhou";
	/**
	 * 初始化短信接口客户端使用
	 */
	public static final String ENDPOINT_NAME = "cn-hangzhou";
	/**
	 * 发送消息返回成功码
	 */
	public static final String SMS_SEND_CODE_OK = "OK";
	/**
	 * 通用短信签名
	 */
	public static final String COMMON_SIGN_NAME="EasyPM官方";
	/**
	 * 用户注册验证码：模板code
	 */
	public static final String REGISTER_VERIFICATION_CODE_TEMPLATE_CODE="SMS_94285269";
}
