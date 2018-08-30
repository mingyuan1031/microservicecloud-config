package com.lwxf.newstore.webapp.common.shiro.matcher.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lwxf.newstore.webapp.common.shiro.matcher.IApiPathPermissionMatcher;
import com.lwxf.newstore.webapp.common.shiro.matcher.IApiPathPermissionMatcherActuator;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:04:59
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class ApiPathPermissionMatcherActuator implements IApiPathPermissionMatcherActuator {
	private List<IApiPathPermissionMatcher> matchers = new ArrayList<>();
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		for(IApiPathPermissionMatcher matcher:this.matchers){
			String ret = matcher.matcher(request,action,servletPath,referer);
			if(null != ret){
				return ret;
			}
		}
		return null;
	}

	@Override
	public void addMatcher(IApiPathPermissionMatcher matcher){
		this.matchers.add(matcher);
	}
}
