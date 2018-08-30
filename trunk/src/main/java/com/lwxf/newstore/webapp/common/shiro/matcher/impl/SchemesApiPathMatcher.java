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
 * @create：2018/8/15 11:41
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SchemesApiPathMatcher implements IApiPathPermissionMatcher {

	/**
	 *	/api/scheme
	 *	/api/scheme
	 *	/api/scheme/{id}
	 *	/api/scheme/{id}/status/{status}
	 *	/api/scheme/{id}
	 *	/api/schemes/{id}/praises
	 *	/api/scheme/{id}/praise
	 *	/api/schemes/{id}/views
	 *	/api/schemes/{type}/files
	 */
	private Pattern pattern = Pattern.compile(LwxfStringUtils.format("/api/schemes(/{0})?", WebConstant.REG_ID_MATCH));
	private Pattern schemesPattern = Pattern.compile(LwxfStringUtils.format("/api/schemes(/{0}(/(status/\\d{1,2}|praises|views|files))?)?", WebConstant.REG_ID_MATCH));

	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		Matcher matcher = pattern.matcher(servletPath);
		boolean isRead = action.equals(WebConstant.REQUEST_ACTION_READ);
		int currUserRole = WebUtils.getCurrUser().getRole().intValue();

		if (matcher.matches())
		{
			if (isRead) {
				return ShiroUtil.WILDCARD_TOKEN;
			}else {
				if (currUserRole!=UserRole.MEMBER.getValue())
				{
					return ShiroUtil.WILDCARD_TOKEN;
				}
			}

		}
		 matcher = schemesPattern.matcher(servletPath);
		if (matcher.matches())
		{
			if (isRead) {
				return ShiroUtil.WILDCARD_TOKEN;
			}else {
				if (currUserRole!=UserRole.MEMBER.getValue())
				{
					return ShiroUtil.WILDCARD_TOKEN;
				}
			}

		}
		return null;
	}
}
