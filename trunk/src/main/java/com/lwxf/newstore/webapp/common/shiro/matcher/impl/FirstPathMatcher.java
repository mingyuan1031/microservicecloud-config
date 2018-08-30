package com.lwxf.newstore.webapp.common.shiro.matcher.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.shiro.matcher.IApiPathPermissionMatcher;
import com.lwxf.newstore.webapp.common.utils.ExceptionGenerateFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 *	功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 12:14:35
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class FirstPathMatcher implements IApiPathPermissionMatcher {
	private Pattern pathPattern = Pattern.compile(LwxfStringUtils.format("^/({0})(/(tasks/{0}(/comments)?)?)?$",WebConstant.REG_ID_MATCH));
	private String ipWhiteListPathTpl = "/api/({0})";
	private List<String> ipWhiteListPathArr = new ArrayList<String>(){{
		add("activities/?.*");
		add("sys/?.*");
	}};
	private String ipWhiteListPathArrStr = LwxfStringUtils.collectionJoin(ipWhiteListPathArr,"|");
	private Pattern orgIpWhiteListPattern =Pattern.compile(LwxfStringUtils.format(ipWhiteListPathTpl,ipWhiteListPathArrStr));
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		Matcher matcher = pathPattern.matcher(servletPath);
		if (matcher.matches()) {
			return ShiroUtil.WILDCARD_TOKEN;
		}
		return null;
	}
}
