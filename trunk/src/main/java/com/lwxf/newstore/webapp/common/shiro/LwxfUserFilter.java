package com.lwxf.newstore.webapp.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lwxf.commons.constant.CommonConstant;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.mobile.WeixinUtils;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 *	功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:09:25
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LwxfUserFilter extends UserFilter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String requestURI = httpServletRequest.getRequestURI();
		// 微信端登陆处理
		if (WebUtils.getCurrUserId() == null && WebUtils.getUserAgent().isWeixin() && ShiroUtil.checkAuthPagePath(requestURI)){
			doWeixinRequest(request, response);
			return false;
		}
		return super.onPreHandle(request, response, mappedValue);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginRequest(request, response)) {
			return true;
		}
		Subject subject = getSubject(request, response);
		// If principal is not null, then the user is known and should be allowed access.
		boolean ret = subject.getPrincipal() != null;
		if (!ret) {
			return ret;
		}
		if (!(AppBeanInjector.configuration.isOnProd() || AppBeanInjector.configuration.isOnTest())) {
			return ret;
		}
		if (WebUtils.isAjaxRequest(request)) {
			return ret;
		}
		if (!WebUtils.getUserAgent().isWeixin()) {
			return ret;
		}
		UserThirdInfo uti = AppBeanInjector.userThirdInfoFacade.findByUserId(WebUtils.getCurrUserId());
		if (uti == null) {
			return ret;
		}
		ret = LwxfStringUtils.isNotEmpty(uti.getWxOpenId());
		if (!ret) {
			subject.logout();
			return false;
		}
		return ret;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (WebUtils.isAjaxRequest(request)) {
			try {
				HttpServletRequest httpReq = (HttpServletRequest) request;
				StringBuilder sb = new StringBuilder();
				LwxfStringUtils.concat(sb,"      ==== 拒绝ajax请求 ====");
				LwxfStringUtils.concat(sb,"            cause : ", WebUtils.getCurrUserId() == null?"用户未登录":"没有权限");
				LwxfStringUtils.concat(sb,"        UserAgent : ", WebUtils.getOriginalUserAgent());
				LwxfStringUtils.concat(sb,"      RequestPath : ", httpReq.getServletPath());
				LwxfStringUtils.concat(sb,"          Referer : ", httpReq.getHeader("Referer"));
				LwxfStringUtils.concat(sb,"      X-PHPSESSID : ", httpReq.getHeader(CommonConstant.REQUEST_HEADER_KEY_X_PHPSESSIONID));
				LwxfStringUtils.concat(sb,"            X-SID : ", httpReq.getHeader(CommonConstant.REQUEST_HEADER_KEY_X_SID));
				LwxfStringUtils.concat(sb,"           X-C-ID : ", httpReq.getHeader(WebUtils.REQUEST_HEADER_X_C_ID));
				LwxfStringUtils.concat(sb,"        sessionId : ", httpReq.getSession().getId());

				Cookie[] cookies = httpReq.getCookies();
				StringBuilder cookieSb = new StringBuilder();
				if(null != cookies){
					for (Cookie cookie : cookies) {
						cookieSb.append(cookie.getName()).append(" = ").append(cookie.getValue()).append("|");
					}
				}
				LwxfStringUtils.concat(sb,"           Cookie : ", cookieSb.length()==0?"没有任何cookie信息":cookieSb.toString());
				logger.error(sb.toString());
			} catch (Exception e) {
				logger.error("调试代码错误");
			}

			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(401);
//			httpResponse.setHeader("Tag", "1");
			response.getWriter().write(WebConstant.REQUEST_RESULT_AJAX_EMPTY_OBJ);
			return false;
		} else {
			if (WebUtils.getUserAgent().isWeixin()) {
				doWeixinRequest(request, response);
				return false;
			}
			return super.onAccessDenied(request, response);
		}
	}


	@Override
	protected void saveRequest(ServletRequest request) {
		super.saveRequest(request);
		HttpServletRequest currRequest = (HttpServletRequest)request;
		String sessionId =currRequest.getSession().getId();
		AppBeanInjector.redisUtils.hPut(sessionId,sessionId,currRequest.getRequestURI(),10,TimeUnit.SECONDS);
	}

	private void doWeixinRequest(ServletRequest request, ServletResponse response) throws Exception{
		saveRequest(request);
		this.logger.debug(" ************ 微信授权通过前，保存当前的请求对象");
		this.logger.debug(" ************ Shiro to saved request path : {}",((HttpServletRequest)request).getRequestURI());
		this.logger.debug(" ************ Shiro to saved request sessionId : {}",((HttpServletRequest)request).getSession().getId());
		String loginUrl = WeixinUtils.getWebAuthorizationPath(WebUtils.getDomainUrl().concat("/wx/login"), AppBeanInjector.wxMpConfigStorage.getAppId(), ((HttpServletRequest) request).getSession().getId());
		WebUtils.issueRedirect(request, response, loginUrl);
	}

	/*@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if(WebUtils.isAjaxRequest(request))
			redirectToLogin(request, response);
		else
			super.onAccessDenied(request, response);
		return false;
	}*/
}
