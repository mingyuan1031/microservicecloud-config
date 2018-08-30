package com.lwxf.newstore.webapp.common.shiro.matcher.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lwxf.commons.constant.CommonConstant;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.shiro.matcher.IApiPathPermissionMatcher;

/**
 * 功能：非法的api路径拦截（放在权限验证的最后一个执行）
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 12:14:35
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class IllegalApiMatcher implements IApiPathPermissionMatcher {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Override
	public String matcher(HttpServletRequest request, String action, String servletPath, String referer) {
		StringBuilder sb = new StringBuilder();
		LwxfStringUtils.concat(sb,"  ******** 出现非法的请求路径 ********  ");
		LwxfStringUtils.concat(sb,"      Request user : ", ShiroUtil.getCurrUserId());
		LwxfStringUtils.concat(sb,"            action : ", action);
		LwxfStringUtils.concat(sb,"       RequestPath : ", servletPath);
		LwxfStringUtils.concat(sb,"           Referer : ", referer);
		LwxfStringUtils.concat(sb,"       X-PHPSESSID : ", request.getHeader(CommonConstant.REQUEST_HEADER_KEY_X_PHPSESSIONID));
		LwxfStringUtils.concat(sb,"             X-SID : ", request.getHeader(CommonConstant.REQUEST_HEADER_KEY_X_SID));
		LwxfStringUtils.concat(sb,"         sessionId : ", request.getSession().getId());
		LwxfStringUtils.concat(sb,"  ********************************  ");
		this.logger.error(sb.toString());
		// TODO：进行非法路径的输出日志及访问黑名单处理
		return WebConstant.STRING_EMPTY;
	}
}
