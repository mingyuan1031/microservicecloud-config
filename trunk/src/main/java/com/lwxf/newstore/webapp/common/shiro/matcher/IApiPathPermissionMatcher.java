package com.lwxf.newstore.webapp.common.shiro.matcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 13:58:46
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface IApiPathPermissionMatcher {
	String matcher(HttpServletRequest request,String action,String servletPath,String referer);
}
