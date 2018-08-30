package com.lwxf.newstore.webapp.common.shiro.matcher;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 18:19:21
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface IApiPathPermissionMatcherActuator extends IApiPathPermissionMatcher {
	void addMatcher(IApiPathPermissionMatcher matcher);
}
