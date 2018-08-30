package com.lwxf.newstore.webapp.common.shiro.matcher.impl;

import javax.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.permission.PermissionIndex;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.shiro.matcher.IApiPathPermissionMatcher;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：店员管理操作权限处理
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-08-08 11:09
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ClerkManageApiPathMatcher implements IApiPathPermissionMatcher {
	/*
	 * /api/users/0/qrcodes/addclerk
	 * /api/users/0/qrcodes/bingshopkeeper
	 * /api/clerks
	 * /api/clerks/{id}
	 * /api/clerks/{id}/roles/{roleValue}
	 *
	 */
	private static final String ADD_CLERK_REQ_PATH = "/api/users/0/qrcodes/addclerk";
	private static final String BIND_SHOPKEEPER = "/api/users/0/qrcodes/bingshopkeeper";
	private String clerkMngRegTemplate = "/api/clerks(/{0}/roles/.d)?";
	private Pattern clerkMngApiPattern = Pattern.compile(LwxfStringUtils.format(clerkMngRegTemplate,WebConstant.REG_ID_MATCH));
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		User currUser = WebUtils.getCurrUser();
		int userRole = currUser.getRole().intValue();
		boolean isShopkeeper = userRole==UserRole.SHOPKEEPER.getValue();
		boolean isManager = userRole == UserRole.MANAGER.getValue();
		// 添加店员二维码生成验证
		if(ADD_CLERK_REQ_PATH.equals(servletPath)){
			if(isShopkeeper || isManager){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_404;
		}
		// 绑定店主二维码生成验证
		if(BIND_SHOPKEEPER.equals(servletPath)){
			if(isShopkeeper){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_404;
		}
		boolean isRead = WebConstant.REQUEST_ACTION_READ.equals(action);

		Matcher matcher = clerkMngApiPattern.matcher(servletPath);
		if(matcher.matches()){
			// 普通会员没有权限
			if(userRole == UserRole.MEMBER.getValue()){
				return WebConstant.STRING_404;
			}

			if(isRead){
				return ShiroUtil.WILDCARD_TOKEN;
			}

			// 普通店员没有权限
			if(userRole == UserRole.CLERK.getValue()){
				return WebConstant.STRING_EMPTY;
			}
			return ShiroUtil.WILDCARD_TOKEN;
		}
		return null;
	}
}
