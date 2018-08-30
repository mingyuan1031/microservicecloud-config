package com.lwxf.newstore.webapp.common.shiro.matcher.impl;

import javax.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.shiro.matcher.IApiPathPermissionMatcher;
import com.lwxf.newstore.webapp.common.utils.WebUtils;

/**
 * 功能：
 *
 * /api/addresses
 * /api/addresses/{id}
 * /api/addresses/{id}/status/{status}
 * /api/users/0/address
 * /api/users/0/address/{aid}/defaulted
 * /api/users/0/address/defaulted
 * @author：wangwangmingyuan
 * @create：2018/8/9 18:11
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class AddressApiPathMatcher implements IApiPathPermissionMatcher {
	private Pattern addressPattern = Pattern.compile(LwxfStringUtils.format("/api/addresses(/{0})?",WebConstant.REG_ID_MATCH));
	private Pattern addressStatusPattern = Pattern.compile(LwxfStringUtils.format("/api/addresses(/{0})?/status/\\d{1,2}",WebConstant.REG_ID_MATCH));
	private Pattern memberPattern = Pattern.compile(LwxfStringUtils.format("/api/users/0/address(/{0})?/defaulted",WebConstant.REG_ID_MATCH));
	/**
	 *
	 * @param request
	 * @param action
	 * @param servletPath
	 * @param referer
	 * @return
	 */
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {

		Matcher matcher = memberPattern.matcher(servletPath);
		// 会员中心的请求
		if(matcher.matches()){
			return ShiroUtil.WILDCARD_TOKEN;
		}
		matcher=addressPattern.matcher(servletPath);
		//后台管理和会员的请求
		if (matcher.matches()){
			// 具体的业务权限验证放在业务逻辑中处理
			return ShiroUtil.WILDCARD_TOKEN;
		}
		matcher=addressStatusPattern.matcher(servletPath);
		if (matcher.matches()){
			return ShiroUtil.WILDCARD_TOKEN;
		}
		return null;
	}
}
