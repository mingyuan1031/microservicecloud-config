package com.lwxf.newstore.webapp.facade.user.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.user.UserExtraService;
import com.lwxf.newstore.webapp.bizservice.user.UserResetpassService;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.user.UserState;
import com.lwxf.newstore.webapp.common.jmail.JMailService;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.UserExtraUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;
import com.lwxf.newstore.webapp.domain.entity.user.UserResetpass;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.user.UserResetpassFacade;

import static com.lwxf.newstore.webapp.facade.AppBeanInjector.userFacade;

/**
 * 功能：用户重置密码操作
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:56:41
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("userResetpassFacade")
public class UserResetpassFacadeImpl extends BaseFacadeImpl implements UserResetpassFacade {
	@Resource(name = "userResetpassService")
	private UserResetpassService userResetpassService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "userExtraService")
	private UserExtraService userExtraService;

	@Override
	@Transactional
	public RequestResult saveResetPass(String email) {
		UserResetpass userResetpass = userResetpassService.findByEmail(email);
		if (userResetpass != null) {
			userResetpassService.deleteById(userResetpass.getId());
		}
		userResetpass = new UserResetpass();
		userResetpass.setCreated(DateUtil.getSystemDate());
		userResetpass.setEmail(email);
		userResetpassService.add(userResetpass);

		//埋数据发送邮件使用
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setClazz(UserResetpass.class);
		tsManualData.setParams(userResetpass);
		//埋了一个对象
		tsManualData.put("userResetpass", userResetpass);
		//重置密码申请事件：直接与发邮件一一对应
		tsManualData.setEvent(JMailService.MAILSEND_CLASSNAME_RESETPASSWORDMAIL);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult saveNewPass(String code, String password,MapContext mapContext) {
		UserResetpass userResetpass = userResetpassService.findById(code);
		if (userResetpass == null) {
			String errMsg = AppBeanInjector.i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001");
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001, errMsg);
		}
		User user = userFacade.findByEmail(userResetpass.getEmail());
		if (user == null) {
			//用户未找到
			String errMsg = AppBeanInjector.i18nUtil.getMessage("BIZ_USER_NOT_FOUND_10002");
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_USER_NOT_FOUND_10002, errMsg);
		}
		//1.判断用户是否激活，这的判断是为了不再进行查密码数据库进行比对
		else  if (user.getState().equals(UserState.ENABLED.getValue())) {
			//用户未激活
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_ACCOUNT_NOT_ACTIVED_00012,AppBeanInjector.i18nUtil.getMessage("SYS_ACCOUNT_NOT_ACTIVED_00012"));
		}
		//2.判断用户是否禁用
		else if (user.getState().equals(UserState.DISABLED.getValue())) {
			//用户被禁用
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_ERROR_USER_ISDISABLED_00019,AppBeanInjector.i18nUtil.getMessage("SYS_ERROR_USER_ISDISABLED_00019"));
		}
		mapContext.put("userId",user.getId());
		RequestResult requestResult = UserExtra.validateFields(mapContext);
		if (requestResult != null) {
			return requestResult;
		}
		//记录重置密码请求日志 // TODO：需要实现
		boolean b = false;
		UserExtra userExtra = userExtraService.findById(user.getId());
		if (userExtra == null) {
			b = true;
			userExtra = new UserExtra();
			userExtra.setUserId(user.getId());
		}
		//加盐
		UserExtraUtil.saltingPassword(userExtra, password);
		userExtra.setUpdated(DateUtil.getSystemDate());
		if (b) {
			userExtraService.add(userExtra);
		} else {
			userExtraService.updateUserExtra(userExtra);
		}
		userResetpassService.deleteById(userResetpass.getId());
		WebUtils.putDataToRequestMap(WebConstant.KEY_RESET_PASSWORD_USER_ID , user.getId());

		return ResultFactory.generateSuccessResult();
	}

	@Override
	public RequestResult saveNewPassword(MapContext mapContext) {
		String email = mapContext.getTypedValue("email",String.class);
		String mobile = mapContext.getTypedValue("mobile",String.class);
		String code = mapContext.getTypedValue("code",String.class);
		User user =null;
		if(email!=null){
			user = userFacade.findByEmail(email);
		}else if(mobile!=null){
			user = userFacade.findByMobile(mobile);
		}
		//用户不存在
		if (user == null) {
			String errMsg = AppBeanInjector.i18nUtil.getMessage("BIZ_USER_NOT_FOUND_10002");
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_USER_NOT_FOUND_10002, errMsg);
		}
		//1.判断用户是否激活，这的判断是为了不再进行查密码数据库进行比对
		else  if (user.getState().equals(UserState.ENABLED.getValue())) {
			//用户未激活
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_ACCOUNT_NOT_ACTIVED_00012,AppBeanInjector.i18nUtil.getMessage("SYS_ACCOUNT_NOT_ACTIVED_00012"));
		}
		//2.判断用户是否禁用
		else if (user.getState().equals(UserState.DISABLED.getValue())) {
			//用户被禁用
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_ERROR_USER_ISDISABLED_00019,AppBeanInjector.i18nUtil.getMessage("SYS_ERROR_USER_ISDISABLED_00019"));
		}

		//记录重置密码请求日志 TODO：需要实现
		UserExtra userExtra = userExtraService.findById(user.getId());
		if (userExtra == null) {
			String errMsg = AppBeanInjector.i18nUtil.getMessage("BIZ_USER_NOT_FOUND_10002");
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_USER_NOT_FOUND_10002, errMsg);
		}
		//加盐
		UserExtraUtil.saltingPassword(userExtra, mapContext.getTypedValue("password",String.class));
		userExtra.setUpdated(DateUtil.getSystemDate());
		userExtraService.updateUserExtra(userExtra);
		WebUtils.putDataToRequestMap(WebConstant.KEY_RESET_PASSWORD_USER_ID , user.getId());
		return ResultFactory.generateSuccessResult();
	}

	@Override
	public UserResetpass findById(String id) {
		return userResetpassService.findById(id);
	}
}