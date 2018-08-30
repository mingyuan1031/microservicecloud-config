package com.lwxf.newstore.webapp.common.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.json.JsonMapper;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.shiro.matcher.IApiPathPermissionMatcherActuator;
import com.lwxf.newstore.webapp.common.shiro.matcher.impl.*;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:09:25
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LwxfShiroUrlFilter extends HttpMethodPermissionFilter {
	protected static final Logger logger = LoggerFactory.getLogger(LwxfShiroUrlFilter.class);
	private static final String KEY_SHIRO_PERM_RES_404 = "shiro_perm_res_404";
	private static final String KEY_SHIRO_PERM_ERROR_RESULT = "shiro_perm_error_result";

	private IApiPathPermissionMatcherActuator matcherActuator;

	public LwxfShiroUrlFilter() {
		matcherActuator = new ApiPathPermissionMatcherActuator();
		matcherActuator.addMatcher(new FirstPathMatcher());
		// TODO：在这里放具体的api解析器，如订单的api解析器
		matcherActuator.addMatcher(new CfgApiPathMatcher()); // 添加系统设置和店铺设置的权限验证
		matcherActuator.addMatcher(new QuickShareApiPathMatcher()); // 添加快享的权限验证
		matcherActuator.addMatcher(new ReservationApiPathMatcher()); // 添加预约的权限验证
		matcherActuator.addMatcher(new GoodsDictApiPathMatcher()); // 添加商品字典素具的权限验证
		matcherActuator.addMatcher(new OrderApiPathMatcher());
		matcherActuator.addMatcher(new AddressApiPathMatcher());
		matcherActuator.addMatcher(new OrderGoodsApiPathMatcher());
		matcherActuator.addMatcher(new LogisticsApiPathMatcher());
		matcherActuator.addMatcher(new LastApiPathMatcher());
		matcherActuator.addMatcher(new SchemesApiPathMatcher());
		matcherActuator.addMatcher(new MyApiPathMatcher());
		// ------------- 我是分割线，在我上边放具体的api解析器 -----------------
		matcherActuator.addMatcher(new NoActiveApiPermissionMatcher());
		matcherActuator.addMatcher(new IllegalApiMatcher());
		this.setLoginUrl("/login");
	}

	@Override
	protected String getHttpMethodAction(String method) {
		String lc = method.toLowerCase();
		String resolved = getHttpMethodActions().get(lc);
		return resolved != null ? resolved : lc;
	}

	protected String buildPermissions(HttpServletRequest request) {
		String action = getHttpMethodAction(request);
		String servletPath = request.getServletPath();
		int lastPos=servletPath.length()-1;
		if(lastPos>=0 && servletPath.charAt(lastPos) == '/'){
			servletPath=servletPath.substring(0,lastPos);
		}
		return this.matcherActuator.matcher(request,action,servletPath,null);
	}

	private boolean hasPrincipal(ServletRequest request, ServletResponse response) {
		Subject subject = getSubject(request, response);
		return subject.getPrincipal() != null;
	}

	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
		try {
			if (!this.hasPrincipal(request, response)) {
				return false;
			}
			WebUtils.putDataToRequestMap("HttpCurLoginUserId", WebUtils.getCurrUserId());
			String permission = buildPermissions(WebUtils.toHttp(request));
			// 有权限
			if(ShiroUtil.WILDCARD_TOKEN.equals(permission)){
				return true;
			}

			// 没权限
			if (LwxfStringUtils.isEmpty(permission)) {
				request.setAttribute("javax.servlet.error.request_uri",((HttpServletRequest)request).getRequestURI());
				return false;
			}

			// 资源不存在
			if(WebConstant.STRING_404.equals(permission)){
				RequestResult result = ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001,AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
				WebUtils.putDataToRequestMap(KEY_SHIRO_PERM_ERROR_RESULT,result);
				return false;
			}

			Assert.isTrue(false,"不合法的权限验证结果：permission = "+permission);
			return false;
		} catch (Exception ex) {
			request.setAttribute("lwxf.excetion", ex);
			logger.error(ex.getMessage());
			throw ex;
		}

	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		RequestResult result = (RequestResult)WebUtils.getDataFromRequestMap(KEY_SHIRO_PERM_ERROR_RESULT);
		if (WebUtils.isAjaxRequest(request) && this.hasPrincipal(request, response)) {
			JsonMapper jsonMapper = new JsonMapper();
			if(null == result){
				result = ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003, AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
			}
			response.getWriter().write(jsonMapper.toJson(result));
			return false;
		}
		return super.onAccessDenied(request, response);
	}
}