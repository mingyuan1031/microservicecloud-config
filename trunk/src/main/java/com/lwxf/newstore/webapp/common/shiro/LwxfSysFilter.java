package com.lwxf.newstore.webapp.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.UserFilter;

import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 *	功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:09:25
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LwxfSysFilter extends UserFilter {
	private static final String ADMIN_URI = "/admin(/.*)?";
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		boolean ret=super.isAccessAllowed(request, response, mappedValue);
		// 非店员用户没有权限登陆管理后台
		if(((HttpServletRequest)request).getRequestURI().equals(ADMIN_URI)){
			if(WebUtils.getCurrUser().getRole().intValue() == UserRole.MEMBER.getValue()){
				return Boolean.FALSE.booleanValue();
			}
		}
		return ret;
	}
}
