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
 * @author：Administrator
 * @create：2018/8/16 9:57
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class MyApiPathMatcher implements IApiPathPermissionMatcher {
	private Pattern myPattern = Pattern.compile(LwxfStringUtils.format("/api/users/0((/paidrecords)|(/orders)|(/address))?"));
	private Pattern addrePattern = Pattern.compile(LwxfStringUtils.format("/api/users/0/address/{0}/defaulted", WebConstant.REG_ID_MATCH));
	private Pattern detailsPattern = Pattern.compile(LwxfStringUtils.format("/api/users/0/orderGoods/{0}/details", WebConstant.REG_ID_MATCH));
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		Matcher matcher = myPattern.matcher(servletPath);
		boolean isRead = action.equals(WebConstant.REQUEST_ACTION_READ);
		int currUserRole = WebUtils.getCurrUser().getRole().intValue();
		if (matcher.matches())
		{
			if (isRead) {
				return ShiroUtil.WILDCARD_TOKEN;
			}else {
				if (currUserRole==UserRole.MEMBER.getValue())
				{
					return ShiroUtil.WILDCARD_TOKEN;
				}
			}

		}
		matcher=addrePattern.matcher(servletPath);
		if (matcher.matches())
		{
			if (isRead) {
				return ShiroUtil.WILDCARD_TOKEN;
			}else {
				if (currUserRole==UserRole.MEMBER.getValue())
				{
					return ShiroUtil.WILDCARD_TOKEN;
				}
			}

		}
		matcher=detailsPattern.matcher(servletPath);
		if (matcher.matches())
		{
			if (isRead) {
				return ShiroUtil.WILDCARD_TOKEN;
			}else {
				if (currUserRole==UserRole.MEMBER.getValue())
				{
					return ShiroUtil.WILDCARD_TOKEN;
				}
			}

		}
		return null;
	}
}
