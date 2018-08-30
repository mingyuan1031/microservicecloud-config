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
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-11 9:11
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ReservationApiPathMatcher implements IApiPathPermissionMatcher {
	/**
	 * /api/users/0/reservations
	 * /api/users/0/reservations
	 * /api/reservations/{id}
	 * /api/reservations
	 */
	private Pattern memberPattern = Pattern.compile(LwxfStringUtils.format("/api/users/0/reservations(/{0})?",WebConstant.REG_ID_MATCH));
	private Pattern reservationsPattern = Pattern.compile(LwxfStringUtils.format("/api/reservations(/{0})?",WebConstant.REG_ID_MATCH));
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		Matcher matcher = memberPattern.matcher(servletPath);
		// 会员中心的请求
		if(matcher.matches()){
			return ShiroUtil.WILDCARD_TOKEN;
		}
		// 管理后台及会员请求
		matcher = reservationsPattern.matcher(servletPath);
		if(matcher.matches()){
			int userRole = WebUtils.getCurrUser().getRole().intValue();
			// 所有店员具有全部权限
			if(userRole != UserRole.MEMBER.getValue()){
				return ShiroUtil.WILDCARD_TOKEN;
			}

			if(action.equals(WebConstant.REQUEST_ACTION_READ)){
				return WebConstant.STRING_404;
			}
			// 具体的业务权限验证放在业务逻辑中处理
			return ShiroUtil.WILDCARD_TOKEN;
		}
		return null;
	}
}
