package com.lwxf.newstore.webapp.baseservice.sms;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.authcode.AuthCodeUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：手机短信服务
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:04:44
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SmsUtil {
	private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	/**
	 * 短信错误返回码
	 */
	private static Map<String,String> errCodeMap= new HashMap<String,String>(25){
		{
			put("isp.RAM_PERMISSION_DENY","RAM权限DENY");
			put("isv.OUT_OF_SERVICE","业务停机");
			put("isv.PRODUCT_UN_SUBSCRIPT","未开通云通信产品的阿里云客户");
			put("isv.PRODUCT_UNSUBSCRIBE","产品未开通");
			put("isv.ACCOUNT_NOT_EXISTS","账户不存在:请确认使用的账号是否与申请的账号一致");
			put("isv.ACCOUNT_ABNORMAL","请确认使用的账号是否与申请的账号一致");
			put("isv.SMS_TEMPLATE_ILLEGAL","短信模板不合法");
			put("isv.SMS_SIGNATURE_ILLEGAL","短信签名不合法");
			put("isv.INVALID_PARAMETERS","参数异常:对照文档，检查参数格式");
			put("isp.SYSTEM_ERROR","请重试接口调用，如仍存在此情况请创建工单反馈工程师查看");
			put("isv.MOBILE_NUMBER_ILLEGAL","非法手机号:PhoneNumbers参数请传入11位国内号段的手机号码");
			put("isv.MOBILE_COUNT_OVER_LIMIT","手机号码数量超过限制:批量上限为1000个手机号码");
			put("isv.TEMPLATE_MISSING_PARAMETERS","模板缺少变量:TemplateParam中需要以json格式字符串给使用的模板中出现的所有变量进行赋值");
			put("isv.BUSINESS_LIMIT_CONTROL","业务限流:默认流控：短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时 ，累计10条/天");
			put("isv.INVALID_JSON_PARAM","JSON参数不合法，只接受字符串值");
			put("isv.BLACK_KEY_CONTROL_LIMIT","黑名单管控:黑名单管控是指变量内容含有限制发送的内容，变量不支持透传url，同时检查通过变量是否透传了一些敏感信息触发关键字");
			put("isv.PARAM_LENGTH_LIMIT","参数超出长度限制:仅对个人用户的限制，单个变量长度限制在15字符内。企业用户无限制");
			put("isv.PARAM_NOT_SUPPORT_URL","变量不支持透传url");
			put("isv.AMOUNT_NOT_ENOUGH","账户余额不足");
			put("isv.TEMPLATE_PARAMS_ILLEGAL","模板变量里包含非法关键字");
			put("SignatureDoesNotMatch","需要注意accessKeyId和accessKeySecret字符串赋值正确无误");
			put("InvalidTimeStamp.Expired","时间戳错误，发出请求的时间和服务器接收到请求的时间不在15分钟内");
			put("SignatureNonceUsed","唯一随机数重复，SignatureNonce为唯一随机数，用于防止网络重放攻击。不同请求间要使用不同的随机数值");
			put("InvalidVersion","版本号错误，需要确认接口的版本号，如云通信短信、语音、流量服务的Version");
			put("InvalidAction.NotFound","接口名错误，需要确认接口地址和接口名，如云通信短信服务短信发送：dysmsapi.aliyuncs.com，接口名Action=SendSms");
		}
	};
	/**
	 * 验证码参数模板
	 */
	private static final String VERIFICATION_CODE_PARAM = "{\"code\":\"{0}\"}";
	/**
	 * 获取找回密码的验证码
	 * @param loginName
	 * @return
	 */
	/*public static String getForgotVerificationCode(String loginName) {
		//先去redis里面查询验证码是否生成
		String key = RedisConstant.SMS_REGISTER_CODE.concat(loginName);
		return redisUtils.getString(key);
	}*/
	/**
	 * 手机注册验证码
	 * @param phoneNumber
	 * @return
	 */
	/*public static String sendRegisterVerificationCode(String phoneNumber){
		//先去redis里面查询验证码是否生成
		String key = RedisConstant.SMS_REGISTER_CODE.concat(phoneNumber);
		String code = redisUtils.getString(key);
		if(LwxfStringUtils.isNoneEmpty(code)){
			//先取验证码，验证码存在，不再变化
			code = AuthCodeUtils.getRandomNumberCode(SmsConstant.VERIFICATION_CODE_LENGTH);
		}
		String param = LwxfStringUtils.format(VERIFICATION_CODE_PARAM,code);
		SendSmsRequest request = new SendSmsRequest();
		request.setSignName(SmsConstant.COMMON_SIGN_NAME);
		String language = AppBeanInjector.i18nUtil.getLanguage();
		//中文语言环境,其他为英文
		if(SmsConstant.ZH_LANGUAGE.equalsIgnoreCase(language)){
			request.setTemplateCode(SmsConstant.REGISTER_VERIFICATION_CODE_TEMPLATE_CODE);
		}else {
			request.setTemplateCode(SmsConstant.REGISTER_VERIFICATION_CODE_TEMPLATE_CODE);
		}
		request.setPhoneNumbers(phoneNumber);
		request.setTemplateParam(param);
		try {
			//连接主机的超时时间（单位：毫秒）
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
			//从主机读取数据的超时时间（单位：毫秒）
			System.setProperty("sun.net.client.defaultReadTimeout", "30000");
			SendSmsResponse sendSmsResponse = smsService.getAcsResponse(request);
			if(sendSmsResponse.getCode() != null && SmsConstant.SMS_SEND_CODE_OK.equalsIgnoreCase(sendSmsResponse.getCode())) {
				//请求成功:把值写入到redis中
				redisUtils.set(key,code,SmsConstant.VERIFICATION_CODE_EXPIRED, TimeUnit.MINUTES);
			}else{
				//打印日志
				logger.error(errCodeMap.get(sendSmsResponse.getCode()));
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}

		return code;
	}*/

	/**
	 * 手机绑定验证码
	 * @param mobile
	 * @return
	 */
	/*public static String sendBindVerificationCode(String userId, String mobile){
		String code = AuthCodeUtils.getRandomNumberCode(SmsConstant.VERIFICATION_CODE_LENGTH);
		redisUtils.set(RedisConstant.MOBILE_BIND.concat(userId), mobile.concat("#").concat(code),SmsConstant.VERIFICATION_CODE_EXPIRED, TimeUnit.MINUTES);

		if(!AppBeanInjector.configuration.isOnFEPublic()){
			logger.debug("非生产环境未发送手机验证码:{}", code);
			return code;
		}

		String param = LwxfStringUtils.format(VERIFICATION_CODE_PARAM,code);
		SendSmsRequest request = new SendSmsRequest();
		request.setSignName(SmsConstant.COMMON_SIGN_NAME);
		String language = AppBeanInjector.i18nUtil.getLanguage();
		//中文语言环境,其他为英文
		if(SmsConstant.ZH_LANGUAGE.equalsIgnoreCase(language)){
			request.setTemplateCode(SmsConstant.REGISTER_VERIFICATION_CODE_TEMPLATE_CODE);
		}else {
			request.setTemplateCode(SmsConstant.REGISTER_VERIFICATION_CODE_TEMPLATE_CODE);
		}
		request.setPhoneNumbers(mobile);
		request.setTemplateParam(param);
		try {
			//连接主机的超时时间（单位：毫秒）
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
			//从主机读取数据的超时时间（单位：毫秒）
			System.setProperty("sun.net.client.defaultReadTimeout", "30000");
			SendSmsResponse sendSmsResponse = smsService.getAcsResponse(request);
			if(sendSmsResponse.getCode() != null && SmsConstant.SMS_SEND_CODE_OK.equalsIgnoreCase(sendSmsResponse.getCode())) {
				//请求成功:把值写入到redis中
			}else{
				//打印日志
				logger.error(errCodeMap.get(sendSmsResponse.getCode()));
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return code;
	}*/

	/**
	 * 发送身份认证验证码
	 * @param mobile
	 * @return
	 */
	/*public static void sendAuthenticationCode(String mobile,String code){
		*//*if(!AppBeanInjector.configuration.isOnFEPublic()){
			logger.debug("非生产环境未发送手机验证码:{}", code);
		}*//*
		String param = LwxfStringUtils.format(VERIFICATION_CODE_PARAM,code);
		SendSmsRequest request = new SendSmsRequest();
		request.setSignName(SmsConstant.COMMON_SIGN_NAME);
		String language = AppBeanInjector.i18nUtil.getLanguage();
		//中文语言环境,其他为英文
		if(SmsConstant.ZH_LANGUAGE.equalsIgnoreCase(language)){
			request.setTemplateCode(SmsConstant.REGISTER_VERIFICATION_CODE_TEMPLATE_CODE);
		}else {
			request.setTemplateCode(SmsConstant.REGISTER_VERIFICATION_CODE_TEMPLATE_CODE);
		}
		request.setPhoneNumbers(mobile);
		request.setTemplateParam(param);
		try {
			//连接主机的超时时间（单位：毫秒）
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
			//从主机读取数据的超时时间（单位：毫秒）
			System.setProperty("sun.net.client.defaultReadTimeout", "30000");
			SendSmsResponse sendSmsResponse = smsService.getAcsResponse(request);
			if((sendSmsResponse.getCode() != null && SmsConstant.SMS_SEND_CODE_OK.equalsIgnoreCase(sendSmsResponse.getCode()))) {
				//打印日志
				logger.error(errCodeMap.get(sendSmsResponse.getCode()));
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * 忘记密码验证码
	 * @param mobile
	 * @return
	 */
	/*public static String sendForgotVerificationCode(String mobile){
		//生成验证码
		String code = AuthCodeUtils.getRandomNumberCode(SmsConstant.VERIFICATION_CODE_LENGTH);
		String param = LwxfStringUtils.format(VERIFICATION_CODE_PARAM,code);
		SendSmsRequest request = new SendSmsRequest();
		request.setSignName(SmsConstant.COMMON_SIGN_NAME);
		String language = AppBeanInjector.i18nUtil.getLanguage();
		//中文语言环境,其他为英文
		if(SmsConstant.ZH_LANGUAGE.equalsIgnoreCase(language)){
			request.setTemplateCode(SmsConstant.REGISTER_VERIFICATION_CODE_TEMPLATE_CODE);
		}else {
			request.setTemplateCode(SmsConstant.REGISTER_VERIFICATION_CODE_TEMPLATE_CODE);
		}
		request.setPhoneNumbers(mobile);
		request.setTemplateParam(param);
		try {
			//连接主机的超时时间（单位：毫秒）
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
			//从主机读取数据的超时时间（单位：毫秒）
			System.setProperty("sun.net.client.defaultReadTimeout", "30000");
			SendSmsResponse sendSmsResponse = smsService.getAcsResponse(request);
			if(sendSmsResponse.getCode() != null && SmsConstant.SMS_SEND_CODE_OK.equalsIgnoreCase(sendSmsResponse.getCode())) {
				//请求成功:把值写入到redis中
				redisUtils.set(RedisConstant.FORGOT_CODE_MOBILE.concat(mobile), code,RedisConstant.FORGOT_CODE_TIME_OUT, TimeUnit.MINUTES);
			}else{
				//打印错误日志
				logger.error(errCodeMap.get(sendSmsResponse.getCode()));
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return code;
	}*/
	/**
	 * 构建发送信息请求对象
	 * @param smsTemplate
	 * @return
	 */
	/*private SendSmsRequest buildSendSmsRequest(SmsTemplate smsTemplate){
		//组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		//必填:待发送手机号
		request.setPhoneNumbers(smsTemplate.getPhoneNumbers());
		//必填:短信签名-可在短信控制台中找到
		request.setSignName(smsTemplate.getSignName());
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(smsTemplate.getTemplateCode());
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam(smsTemplate.getTemplateParam());
		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId(smsTemplate.getOutId());
		return request;
	}*/

	/*public static void main(String[] args) {
		String phoneNumber = "15503717899";
		String code = sendRegisterVerificationCode(phoneNumber);
		System.out.println(code);
	}*/
}
