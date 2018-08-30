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

/**
 * 功能：系统设置和店铺设置权限控制
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-09 10:35
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class CfgApiPathMatcher implements IApiPathPermissionMatcher {
	private String REG_GROUP_SYSCFGS="syscfgs";
	private String REG_GROUP_STORECFGS="storecfgs";
	/**
	 * /api/syscfgs/:id
	 * /api/syscfgs/:id/wxmenus
	 * /api/storecfgs/:id
	 * /api/storecfgs/:id/files
	 */
	private String patternPaths = "/api/({0}/{2}(/wxmenus)?|{1}/{2}(/files)?)";
	private Pattern cfgPattern = Pattern.compile(LwxfStringUtils.format(patternPaths,REG_GROUP_SYSCFGS,REG_GROUP_STORECFGS,WebConstant.REG_ID_MATCH));
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		Matcher matcher = this.cfgPattern.matcher(servletPath);
		if(matcher.matches()){
			int currUserRole = WebUtils.getCurrUser().getRole().intValue();
			// 店主验证
			if(currUserRole == UserRole.SHOPKEEPER.getValue()){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			// 普通会员验证
			if(currUserRole == UserRole.MEMBER.getValue()){
				return WebConstant.STRING_EMPTY;
			}
			// 店员验证
			int permIndx = PermissionIndex.CfgPermission.SYS.getIndex();
			String cfgGroup = matcher.group(1);
			if(cfgGroup.equals(REG_GROUP_STORECFGS)){
				permIndx = PermissionIndex.CfgPermission.STORE.getIndex();
			}

			if(ShiroUtil.isPermitted(PermissionIndex.PERMISSION_CFG_PREFIX.concat(String.valueOf(permIndx)))){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_EMPTY;
		}
		return null;
	}
}
