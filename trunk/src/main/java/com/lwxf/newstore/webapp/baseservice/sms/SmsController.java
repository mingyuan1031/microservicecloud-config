package com.lwxf.newstore.webapp.baseservice.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.controller.path.LoadBaseCfgController;

/**
 * 功能：发送手机消息外部调用服务
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 8:51:28
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/sms")
public class SmsController extends LoadBaseCfgController {
	private static final Logger logger = LoggerFactory.getLogger(SmsController.class);
	@PostMapping("/{type}/phonenumbers/{phoneNumber}")
	public RequestResult sendSms(@PathVariable String type,@PathVariable String phoneNumber){
		RequestResult requestResult=null;
		switch (type) {
			case  "register":
				requestResult = checkAttackLockRegister(phoneNumber);
				if(requestResult!=null){
					return requestResult;
				}
				 /* TODO：暂时屏蔽
				 SmsUtil.sendRegisterVerificationCode(phoneNumber);
				 */
				return ResultFactory.generateSuccessResult();
			case  "forgot":
				requestResult = checkAttackLockForgot(phoneNumber);
				if(requestResult!=null){
					return requestResult;
				}
				/* TODO：暂时屏蔽
				 SmsUtil.sendRegisterVerificationCode(phoneNumber);
				 */
				return ResultFactory.generateSuccessResult();
			default:
				logger.warn("发送手机短信，非法的请求类型!");
				return  ResultFactory.generateSuccessResult();

		}
	}
	@GetMapping("/{type}/phonenumbers/{phoneNumber}")
	public RequestResult getSms(@PathVariable String type,@PathVariable String phoneNumber){
		switch (type) {
			case  "register":
				/* TODO：暂时屏蔽
				String code = SmsUtil.getRegisterVerificationCode(phoneNumber);
				*/
				String code = null;/*SmsUtil.getRegisterVerificationCode(phoneNumber);*/
				return ResultFactory.generateRequestResult(code);
			default:
				logger.warn("获取手机短信验证码，非法的请求类型!");
				return  ResultFactory.generateSuccessResult();

		}
	}

	/**
	 * 密码找回手机短信码加防攻击锁
	 * @param mobile
	 * @return
	 */
	private RequestResult checkAttackLockForgot(String mobile){
		/*String ip = WebUtils.getClientIpAddress();
		String key = RedisConstant.CODE_ATTACK_LOCK_FORGOT_IP.concat(ip);
		boolean locked = RedisAttackLock.checkLocked(key,RedisConstant.CODE_ATTACK_LOCK_FORGOT_IP_LIMIT,RedisConstant.CODE_ATTACK_LOCK_FORGOT_IP_TIMEOUT,TimeUnit.DAYS);
		if(locked){
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_ERROR_CODE_IP_LIMIT_00021, AppBeanInjector.i18nUtil.getMessage("SYS_ERROR_CODE_IP_LIMIT_00021"));
		}else {
			String mobileKey = RedisConstant.CODE_ATTACK_LOCK_FORGOT.concat(mobile);
			boolean mobileLocked = 	RedisAttackLock.checkLocked(mobileKey,RedisConstant.CODE_ATTACK_LOCK_FORGOT_LIMIT,RedisConstant.CODE_ATTACK_LOCK_FORGOT_TIMEOUT,TimeUnit.DAYS);
			if(mobileLocked){
				return ResultFactory.generateErrorResult(ErrorCodes.SYS_ERROR_CODE_MOBILEOREMAIL_LIMIT_00022, AppBeanInjector.i18nUtil.getMessage("SYS_ERROR_CODE_MOBILEOREMAIL_LIMIT_00022"));
			}
		}*/
		return null;
	}

	/**
	 * 注册：手机短信码加防攻击锁
	 * @param mobile
	 * @return
	 */
	private RequestResult checkAttackLockRegister(String mobile){
		/*String ip = WebUtils.getClientIpAddress();
		String key = RedisConstant.CODE_ATTACK_LOCK_REGISTER_IP.concat(ip);
		boolean locked = RedisAttackLock.checkLocked(key,RedisConstant.CODE_ATTACK_LOCK_REGISTER_IP_LIMIT,RedisConstant.CODE_ATTACK_LOCK_REGISTER_IP_TIMEOUT,TimeUnit.DAYS);
		if(locked){
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_ERROR_CODE_IP_LIMIT_00021, AppBeanInjector.i18nUtil.getMessage("SYS_ERROR_CODE_IP_LIMIT_00021"));
		}else {
			String mobileKey = RedisConstant.CODE_ATTACK_LOCK_REGISTER.concat(mobile);
			boolean mobileLocked = 	RedisAttackLock.checkLocked(mobileKey,RedisConstant.CODE_ATTACK_LOCK_REGISTER_LIMIT,RedisConstant.CODE_ATTACK_LOCK_REGISTER_TIMEOUT,TimeUnit.DAYS);
			if(mobileLocked){
				return ResultFactory.generateErrorResult(ErrorCodes.SYS_ERROR_CODE_MOBILEOREMAIL_LIMIT_00022, AppBeanInjector.i18nUtil.getMessage("SYS_ERROR_CODE_MOBILEOREMAIL_LIMIT_00022"));
			}
		}*/
		return null;
	}
}
