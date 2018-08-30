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
 * 广告位
 * 工厂视频
 * 首页导航
 * 设计案例
 * 会员管理
 * @author：wangmingyuan
 * @create：2018/8/14 9:45
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LastApiPathMatcher implements IApiPathPermissionMatcher {

	/**
	 *广告位
	 * /api/advertisings/files
	 * /api/advertisings
	 * /api/advertisings/{id}
	 * 视频类型
	 * /api/videotypes
	 * 视频文件
	 *
	 */
	private Pattern advertisingsPattern = Pattern.compile(LwxfStringUtils.format("/api/advertisings((/files)?|/{0}(/linkStart)?)?",WebConstant.REG_ID_MATCH));
	private Pattern videotypesPattern = Pattern.compile(LwxfStringUtils.format("/api/videotypes(/{0})?",WebConstant.REG_ID_MATCH));
	private Pattern videofilesPattern = Pattern.compile(LwxfStringUtils.format("/api/videofiles((/files)?|(/video)?|/{0})?",WebConstant.REG_ID_MATCH));
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		Matcher matcher = advertisingsPattern.matcher(servletPath);
		boolean isRead = action.equals(WebConstant.REQUEST_ACTION_READ);
		int currUserRole = WebUtils.getCurrUser().getRole().intValue();
		/**
		 * 广告位
		 */
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
		/**
		 * 视频类型
		 */
		matcher=videotypesPattern.matcher(servletPath);
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
		/**
		 * 视频文件
		 */
		matcher = videofilesPattern.matcher(servletPath);
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
