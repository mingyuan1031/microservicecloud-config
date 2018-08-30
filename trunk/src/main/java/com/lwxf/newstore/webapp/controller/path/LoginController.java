package com.lwxf.newstore.webapp.controller.path;

import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.apache.shiro.web.util.SavedRequest;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.baseservice.cache.constant.RedisConstant;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.mobile.WeixinUtils;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.user.UserThirdInfoFacade;

/**
 * 功能：web微信登录相关
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:18:00
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Controller
public class LoginController extends LoadBaseCfgController {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	private static final String WEIXIN_LOGIN_REDIRECT_URI = "/wx/wxlogin";
	@Resource
	private UserThirdInfoFacade userThirdInfoFacade;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String goLogin(ModelMap model) {
		User currUser = WebUtils.getCurrUser();
		if (currUser != null) {
			model.clear();
		}
		return WebUtils.getCurrUserDefaultRedirectPath(currUser,WebUtils.getResponsePagePath("login"));
	}


	@RequestMapping(value = "/wx/login")
	public String goToAuth(final HttpServletRequest request, final HttpServletResponse response, @RequestParam(required = false) String code, @RequestParam(required = false) String v, @RequestParam(required = false) String state) throws Exception {
		if (LwxfStringUtils.isBlank(code) || LwxfStringUtils.isBlank(state)) {
			logger.debug("微信回调code或state为空");
			return "redirect:/login";
		}

		String openId = null;
		try {
			WxMpOAuth2AccessToken wMpOAuth2AccessToken = AppBeanInjector.wxMpService.oauth2getAccessToken(code);
			openId = wMpOAuth2AccessToken.getOpenId();
		} catch (Exception e) {
			logger.error("获取用户的微信信息出现异常", e);
		}

		if (LwxfStringUtils.isBlank(openId)) {
			return "redirect:/login";
		}

		Integer platformTag = (Integer) AppBeanInjector.redisUtils.hGet(RedisConstant.PLATFORM_TAG, openId);
		if (platformTag == null) {
			platformTag = WeixinUtils.initPlatformTag(openId);
		}

		if (platformTag == null) {
			logger.debug("当前用户未绑定账号，跳转登陆页面");
			request.getSession().setAttribute("weixin_openid", openId);
			return "redirect:/login";
		}

		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		if (WebUtils.getUserAgent().isWeixin() && savedRequest != null) {
			String reqPath = savedRequest.getRequestURI();
			if (ShiroUtil.checkAuthPagePath(reqPath)) {
				if (platformTag == 0) {
					WxMpUser wxMpUser = WeixinUtils.getWxMpUserByOpenId(openId);
					AppBeanInjector.weixinFacade.createUser(wxMpUser,UserRole.MEMBER);
				}
				return AppBeanInjector.userThirdInfoFacade.doWxAuthLogin(openId);
			}
		}
		return WebUtils.getCurrUserDefaultPage();
	}

	/**
	 * web微信登陆回调
	 *
	 * @param code  微信传入code 用来获取token和用户信息
	 * @param state 二维码生成进传入参数，可做验证
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/wx/wxlogin")
	public String wxLogin(@RequestParam(required = false) String code, @RequestParam(required = false) String state, ModelMap model) {
		if (LwxfStringUtils.isBlank(code) || LwxfStringUtils.isBlank(state)) {
			logger.debug("微信回调code或state为空");
			return "redirect:/login";
		}
		// TODO: 2017/8/2  校验state csrf (暂不实现 2017.10.10 zhaozhenyi)
		if (!"weixin".equals(state)) {
			logger.debug("二维码请求state不一致");
			return "redirect:/login";
		}

		//通过code获取access_token
		JSONObject jsonObject = WeixinUtils.getWebAuthorizeInfo(code);
		if (jsonObject.has("errcode")) {
			logger.debug("调用微信接口返回错误:{}", jsonObject);
			return "redirect:/login";
		}

		String accessToken = (String) jsonObject.get("access_token");
		String openId = (String) jsonObject.get("openid");
		UserThirdInfo userThirdInfo = WeixinUtils.getUserWeixinInfoForWeb(openId, accessToken);


		if (userThirdInfo == null) {
			logger.debug("查询微信用户为空");
			return "redirect:/login";
		}
		return userThirdInfoFacade.wxQRCodeScan(userThirdInfo);
	}
}
