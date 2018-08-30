package com.lwxf.newstore.webapp.common.utils;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.Assert;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import com.lwxf.commons.agent.LwxfUserAgent;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.MainAccountType;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.enums.user.UserSex;
import com.lwxf.newstore.webapp.common.enums.user.UserState;
import com.lwxf.newstore.webapp.common.mobile.WeixinUtils;
import com.lwxf.newstore.webapp.common.shiro.LwxfShiroRealm;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：用户密码加盐工具
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:48:35
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class UserExtraUtil {
	private static final Logger logger = LoggerFactory.getLogger(UserExtraUtil.class);

	public static UserExtra saltingPassword(UserExtra userExtra, String newPassword) {
		if (userExtra != null && newPassword != null) {
			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			String salt = rng.nextBytes().toBase64();
			String hashedPasswordBase64 = new SimpleHash(LwxfShiroRealm.HASH_ALGORITHM, newPassword, salt, LwxfShiroRealm.HASH_INTERATIONS)
					.toBase64();
			userExtra.setSalt(salt);
			userExtra.setPassword(hashedPasswordBase64);
			String token = new SimpleHash(LwxfShiroRealm.HASH_ALGORITHM, rng.nextBytes().toBase64(), salt, LwxfShiroRealm.HASH_INTERATIONS)
					.toBase64();
			userExtra.setToken(token);
		}
		return userExtra;
	}

	public static UserExtra newUserExtra(String userId) {
		UserExtra userExtra = new UserExtra();
		userExtra.setUserId(userId);
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		String salt = rng.nextBytes().toBase64();
		userExtra.setSalt(salt);
		String token = new SimpleHash(LwxfShiroRealm.HASH_ALGORITHM, rng.nextBytes().toBase64(), salt, LwxfShiroRealm.HASH_INTERATIONS)
				.toBase64();
		userExtra.setToken(token);
		userExtra.setUpdated(DateUtil.getSystemDate());
		return userExtra;
	}

	/**
	 * 用户邮箱构造函数
	 *
	 * @param email
	 * @return
	 */
	public static User userNewOne(String email) {
		Assert.notNull(email, "邮箱不能为空!");
		User user = new User();
		//初始化数据使用
		//用户名
		int index = email.indexOf("@");
		user.setName(email.substring(0, index));
		//用户信箱
		user.setEmail(email);
		//用户头像，时区，语言默认值
		user.setAvatar(AppBeanInjector.configuration.getUserDefaultAvatar());
		user.setTimeZone(WebConstant.TIMEZONE_DEFAULT);
		user.setLanguage(WebConstant.LANGUAGE_DEFAULT);
		user.setState(UserState.ENABLED.getValue());
		user.setCreated(DateUtil.getSystemDate());
		return user;
	}

	/**
	 * 用户其他默认信息初始化设置
	 *
	 * @param user
	 * @return
	 */
	public static void setOtherInfo(User user) {
		if (user == null) {
			logger.error("用户其他默认信息初始化时，传入参数为null");
			return;
		}
		//初始化数据使用
		//用户头像，时区，语言默认值
		user.setAvatar(AppBeanInjector.configuration.getUserDefaultAvatar());
		user.setTimeZone(WebConstant.TIMEZONE_DEFAULT);
		user.setLanguage(WebConstant.LANGUAGE_DEFAULT);
		user.setCreated(DateUtil.getSystemDate());
	}

	/**
	 * @param userThirdInfo
	 * @return
	 */
	public static User userNewWeixin(UserThirdInfo userThirdInfo) {
		User user = new User();
		user.setName(userThirdInfo.getWxNickname());
		user.setAvatar(AppBeanInjector.configuration.getUserDefaultAvatar());
		user.setTimeZone(WebConstant.TIMEZONE_DEFAULT);
		user.setLanguage(WebConstant.LANGUAGE_DEFAULT);
		user.setCreated(DateUtil.getSystemDate());
		user.setState(UserState.ENABLED.getValue());
		return user;
	}

	/**
	 * @param wxMpUser
	 * @return
	 */
	public static User createUserByWxMpUser(WxMpUser wxMpUser) {
		User user = new User();
		user.setName(wxMpUser.getNickname());
		user.setUsername(wxMpUser.getOpenId());
		user.setSex(WeixinUtils.transitionWeiXinUserSex(wxMpUser.getSex()));
		user.setAvatar(AppBeanInjector.configuration.getUserDefaultAvatar());
		user.setTimeZone(WebConstant.TIMEZONE_DEFAULT);
		user.setLanguage(WebConstant.LANGUAGE_DEFAULT);
		user.setCreated(DateUtil.getSystemDate());
		user.setState(UserState.ENABLED.getValue());
		user.setRole(UserRole.MEMBER.getValue());
		return user;
	}

	/**
	 * @param wxMpUser
	 * @return
	 */
	public static MapContext createUserThirdInfoMapByWxMpUser(WxMpUser wxMpUser) {
		MapContext update = MapContext.newOne();
		update.put("wxOpenId", wxMpUser.getOpenId());
		update.put("wxUnionId", wxMpUser.getUnionId());
		update.put("wxNickname", wxMpUser.getNickname());
		update.put("wxIsSubscribe", Boolean.TRUE);
		update.put("wxIsBind", Boolean.TRUE);
		return update;
	}

	/**
	 * 邮箱注册第三方信息初始化
	 *
	 * @param userId
	 * @return
	 */
	public static UserThirdInfo userThirdInfoInitFromEmailRegister(String userId) {
		UserThirdInfo userThirdInfo = new UserThirdInfo();
		userThirdInfo.setUserId(userId);
		userThirdInfo.setEmailIsBind(Boolean.TRUE);
		userThirdInfo.setMobileIsBind(Boolean.FALSE);
		userThirdInfo.setWxIsBind(Boolean.FALSE);
		userThirdInfo.setWxIsSubscribe(Boolean.FALSE);
		return userThirdInfo;
	}

	/**
	 * 手机号注册第三方信息初始化
	 *
	 * @param userId
	 * @return
	 */
	public static UserThirdInfo userThirdInfoInitFromMobileRegister(String userId) {
		UserThirdInfo userThirdInfo = new UserThirdInfo();
		userThirdInfo.setUserId(userId);
		userThirdInfo.setEmailIsBind(Boolean.FALSE);
		userThirdInfo.setMobileIsBind(Boolean.TRUE);
		userThirdInfo.setWxIsBind(Boolean.FALSE);
		userThirdInfo.setWxIsSubscribe(Boolean.FALSE);
		return userThirdInfo;
	}

	/**
	 * 设备信息转字符串工具方法
	 *
	 * @param chUserAgent
	 * @return
	 */
	public static String userAgentSubString(LwxfUserAgent chUserAgent) {
//		增加 chUserAgent为 null异常处理
		if (chUserAgent == null) {
			return null;
		}
		String str = chUserAgent.toString();
		if (str.length() > 300) {
			if (logger.isDebugEnabled()) {
				logger.debug(str);
			}
			str = str.substring(0, 299);
		}
		return str;
	}

	public static UserThirdInfo initUserThirdInfoByAccountType(String userId, Integer mainAccount) {
		switch (MainAccountType.values()[mainAccount]) {
			case EMAIL:
				return userThirdInfoInitFromEmailRegister(userId);
			case MOBILE:
				return userThirdInfoInitFromMobileRegister(userId);
			case WEIXIN:
				break;
			case GITHUB:
				break;
			case WEIBO:
				break;
			case DING:
				break;
			case QQ:
				break;
			default:
				return null;
		}
		return null;
	}
}
