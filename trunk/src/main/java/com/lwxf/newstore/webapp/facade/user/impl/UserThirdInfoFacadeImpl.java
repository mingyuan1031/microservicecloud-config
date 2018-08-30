package com.lwxf.newstore.webapp.facade.user.impl;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.security.attacklocker.IAttackLocker;
import com.lwxf.commons.security.attacklocker.impl.AttackLockerInfo;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.user.UserExtraService;
import com.lwxf.newstore.webapp.bizservice.user.UserThirdInfoService;
import com.lwxf.newstore.webapp.common.constant.UserActivityEvent;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.UserExtraUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.user.UserThirdInfoFacade;

import static com.lwxf.newstore.webapp.facade.AppBeanInjector.*;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:56:55
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("userThirdInfoFacade")
public class UserThirdInfoFacadeImpl extends BaseFacadeImpl implements UserThirdInfoFacade {
	private Logger logger = LoggerFactory.getLogger(UserThirdInfoFacadeImpl.class);

	@Resource
	private UserThirdInfoService userThirdInfoService;
	@Autowired
	@Qualifier("loginAttackLocker")
	protected IAttackLocker loginAttackLocker;
	@Resource
	private UserExtraService userExtraService;

	@Override
	public List<UserThirdInfo> findByWxOpenId(String wxOpenId) {
		return this.userThirdInfoService.findByWxOpenId(wxOpenId);
	}

	@Override
	public RequestResult findUserThirdInfo() {
		String userId = WebUtils.getCurrUserId();
		UserThirdInfo userThirdInfo = this.userThirdInfoService.findByUserId(userId);

		Map map = new HashMap();
		if(userThirdInfo != null){
			map.put("userId", userThirdInfo.getUserId());
			if(!LwxfStringUtils.isBlank(userThirdInfo.getWxNickname())){
				map.put("wxNickname", userThirdInfo.getWxNickname());
			}

			if(userThirdInfo.getWxIsBind() != null){
				map.put("wxIsBind", userThirdInfo.getWxIsBind());
			}

			if(userThirdInfo.getEmailIsBind() != null){
				map.put("emailIsBind", userThirdInfo.getEmailIsBind());
			}

			if(userThirdInfo.getMobileIsBind() != null){
				map.put("mobileIsBind", userThirdInfo.getMobileIsBind());
			}
		}

		User user = userService.findById(WebUtils.getCurrUserId());
		if(!LwxfStringUtils.isBlank(user.getEmail())){
			map.put("email", user.getEmail());
		}

		if(!LwxfStringUtils.isBlank(user.getMobile())){
			map.put("mobile", user.getMobile());
		}
		return ResultFactory.generateRequestResult(map);
	}

	@Override
	public UserThirdInfo findByUserId(String userId) {
		return this.userThirdInfoService.findByUserId(userId);
	}

	/**
	 * 微信端网页授权回调登陆，不记录登陆日志和重置状态
	 * @param openId
	 * @return
	 */
	@Override
	public String doWxAuthLogin(String openId) {
		List<UserThirdInfo> userThirdInfoList = userThirdInfoService.findByWxOpenId(openId);
		if (userThirdInfoList.isEmpty()) {

			WebUtils.getHttpServletRequest().getSession().setAttribute("weixin_openid", openId);
			return "redirect:/login";
		}

		UserThirdInfo bindUserInfo = null;
		UserThirdInfo mainUserInfo = null;
		for (UserThirdInfo userInfo : userThirdInfoList) {
			if (userInfo.getWxIsBind()) {
				bindUserInfo = userInfo;
			} else {
				mainUserInfo = userInfo;
			}
		}

		String userId = null;
		//直接登陆绑定用户
		if (bindUserInfo != null) {
			userId = bindUserInfo.getUserId();
		}else{
			//登陆主账号，如果是非微信主账号，微信信息在解绑时会被删除，所以非绑定微信且存在即为主账号
			if (mainUserInfo != null) {
				userId = mainUserInfo.getUserId();
			}
		}

		if(userId == null){
			return "redirect:/login";
		}

		Subject subject = SecurityUtils.getSubject();
		UserExtra userExtra = userExtraService.findById(userId);
		UsernamePasswordToken userToken = new UsernamePasswordToken(userId, userExtra.getToken());
		subject.login(userToken);
		userToken.clear();

		HttpServletRequest request = WebUtils.getHttpServletRequest();
		request.setAttribute("domainUrl", AppBeanInjector.configuration.getDomainUrl());

		return WebUtils.redirectToSavedRequest(request, "/mall");
	}

	@Override
	@Transactional
	public RequestResult fixUserWxUnionId(){
		List<UserThirdInfo> userThirdInfoList = userThirdInfoService.findAllWithNotBlankWxOpenId();
		List<String> openidList = new ArrayList<>(userThirdInfoList.size());

		for (UserThirdInfo userThirdInfo : userThirdInfoList){
			if(!LwxfStringUtils.isBlank(userThirdInfo.getWxOpenId())){
				openidList.add(userThirdInfo.getWxOpenId());
			}else {
				//修正为空串的openid为null，以后统一为null
				if(userThirdInfo.getWxOpenId() != null){
					MapContext mapContext = MapContext.newOne();
					mapContext.put("wxOpenId", null);
					mapContext.put("userId", userThirdInfo.getUserId());
					userThirdInfoService.updateByMapContext(mapContext);
				}
			}
		}

		WxMpUserService wxMpUserService = wxMpService.getUserService();
		int begin = 0;
		boolean flag = true;
		while(flag && begin < openidList.size()){
			int end = begin + 100;
			List<String> openIdsTmp = openidList.subList(begin,  end > openidList.size() ? openidList.size() :end);

			if(openIdsTmp == null || openIdsTmp.isEmpty()){
				break;
			}

			try {
				List<WxMpUser> wxMpUserList = wxMpUserService.userInfoList(openIdsTmp);

				for (WxMpUser wxMpUser : wxMpUserList){
					MapContext mapContext = MapContext.newOne();
					mapContext.put("wxOpenId", wxMpUser.getOpenId());
					mapContext.put("wxUnionId", wxMpUser.getUnionId());
					userThirdInfoService.updateByWxOpenId(mapContext);
				}
				begin += 100;
			} catch (WxErrorException e) {
				flag = false;
				logger.error(e.getMessage());
			}
		}

		if (flag){
			return ResultFactory.generateSuccessResult();
		}
		return ResultFactory.generateErrorResult(ErrorCodes.SYS_EXECUTE_FAIL_00001, i18nUtil.getMessage("SYS_EXECUTE_FAIL_00001"));
	}


	@Override
	@Transactional
	public String wxQRCodeScan(UserThirdInfo userThirdInfo) {
		//通过开发平台UnionId查询数据库用户,只查询已绑定的
		List<UserThirdInfo> userThirdInfoList = userThirdInfoService.findByWxUnionId(userThirdInfo.getWxUnionId());
		if (userThirdInfoList.isEmpty()) {
			//走注册流程
			return wxRegister(userThirdInfo);
		}

		UserThirdInfo bindUserInfo = null;
		UserThirdInfo mainUserInfo = null;
		for (UserThirdInfo userInfo : userThirdInfoList) {
			if (userInfo.getWxIsBind()) {
				bindUserInfo = userInfo;
			} else {
				mainUserInfo = userInfo;
			}
		}

		//直接登陆绑定用户
		if (bindUserInfo != null) {
			return toLogin(bindUserInfo);
		}

		//登陆主账号，如果是非微信主账号，微信信息在解绑时会被删除，所以非绑定微信且存在即为主账号
		if (mainUserInfo != null) {
			//主账号登陆自动变为绑定状态
			MapContext mapContext = MapContext.newOne();
			mapContext.put("userId", mainUserInfo.getUserId());
			mapContext.put("wxIsBind", Boolean.TRUE);
			userThirdInfoService.updateByMapContext(mapContext);

			return toLogin(mainUserInfo);
		}
		return "redirect:/login";
	}

	private String toLogin(UserThirdInfo userThirdInfo) {
		String userId = userThirdInfo.getUserId();
		//登陆setcookie
		Subject subject = SecurityUtils.getSubject();
		UserExtra userExtra = userExtraService.findById(userId);
		UsernamePasswordToken userToken = new UsernamePasswordToken(userId, userExtra.getToken());
		if(!WebUtils.getUserAgent().isWeixin()){
			userToken.setRememberMe(true);
		}

		subject.login(userToken);
		userToken.clear();

		//判断用户被锁定
		AttackLockerInfo attackLockerInfo = (AttackLockerInfo) this.loginAttackLocker.getLockerInfo(userId);
		if (attackLockerInfo.isLocked()) {
			attackLockerInfo.unlock();
		}

		//记录登陆日志 // TODO
		//更新最后登陆时间
		MapContext userMap = MapContext.newOne();
		userMap.put("id", userId);
		userMap.put("lastLogin", DateUtil.getSystemDate());
		userService.updateByMapContext(userMap);

		logger.debug("用户:{} 微信登陆成功", userId);

		return WebUtils.redirectToSavedRequest(WebUtils.getHttpServletRequest(), "/");
	}

	/**
	 * 注册
	 *
	 * @param userThirdInfo
	 * @return
	 */
	private String wxRegister(UserThirdInfo userThirdInfo) {
		//保存user
		User user = UserExtraUtil.userNewWeixin(userThirdInfo);
		userService.add(user);

		//保存密码表
		UserExtra userExtra = UserExtraUtil.newUserExtra(user.getId());
		userExtraService.add(userExtra);

		//保存第三方信息
		userThirdInfo.setUserId(user.getId());
		userThirdInfo.setWxIsSubscribe(Boolean.FALSE);
		userThirdInfo.setWxIsBind(Boolean.TRUE);
		userThirdInfo.setEmailIsBind(Boolean.FALSE);
		userThirdInfo.setMobileIsBind(Boolean.FALSE);
		//因为数据库只需要存储wxUniond和公众号的wxopenId
		userThirdInfo.setWxOpenId(null);
		userThirdInfoService.add(userThirdInfo);

		return toLogin(userThirdInfo);
	}
}
