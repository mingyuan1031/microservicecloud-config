package com.lwxf.newstore.webapp.common.shiro.matcher.impl;

import javax.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.permission.PermissionIndex;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.shiro.matcher.IApiPathPermissionMatcher;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/8/10 20:10
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LogisticsApiPathMatcher implements IApiPathPermissionMatcher {


	/**
	 *
	 * /api/logistics
	 */

	private Pattern  logisticsPattern = Pattern.compile(LwxfStringUtils.format("/api/logistics(/{0})?",WebConstant.REG_ID_MATCH));
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		Matcher matcher=logisticsPattern.matcher(servletPath);
		boolean isRead = action.equals(WebConstant.REQUEST_ACTION_READ);
		String perm;
		if (matcher.matches())
		{
			if(isRead){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			if(action.equals(WebConstant.REQUEST_ACTION_CREATE)){
				perm = PermissionIndex.PERMISSION_LOGISTICS_PREFIX.concat(String.valueOf(PermissionIndex.LogisticsPermssion.ADD.getIndex()));
			}else if(action.equals(WebConstant.REQUEST_ACTION_UPDATE)){
				perm = PermissionIndex.PERMISSION_LOGISTICS_PREFIX.concat(String.valueOf(PermissionIndex.LogisticsPermssion.MD.getIndex()));
			}else { // 删除
				perm = PermissionIndex.PERMISSION_LOGISTICS_PREFIX.concat(String.valueOf(PermissionIndex.LogisticsPermssion.DELETE.getIndex()));
			}
			if(ShiroUtil.isPermitted(perm)){
				return ShiroUtil.WILDCARD_TOKEN;
			}
			return WebConstant.STRING_404;
		}
		return null;
	}
}
