package com.lwxf.newstore.webapp.web.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lwxf.commons.agent.LwxfUserAgent;
import com.lwxf.commons.constant.CommonConstant;
import com.lwxf.commons.exception.ResNotFoundException;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.baseservice.security.csrf.CsrfService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.utils.SpringContextUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:40:03
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LwxfFirstFilter extends OncePerRequestFilter {
	private final static Pattern disablePath = Pattern.compile(".*?\\.(jsp|php|asp)|.*?Match1.*|/(admin|manager|script|scripts|html)/.*?",Pattern.CASE_INSENSITIVE);
	private final static Pattern disableUri=Pattern.compile(".*?\\;\\..*?|",Pattern.CASE_INSENSITIVE);
	//http://www.wooyun.org/bugs/wooyun-2015-096676 用友某系统任意文件下载
	// 		GET //hrss/dorado/smartweb2.loadConst.d?language=zh&country=\\..\\..\\..\\..\\..\\..\\..\\..\\..\\..\\..\\..\\windows\\system32\\drivers\\etc\\hosts%00.html HTTP/1.1
	private final static Pattern disableQry = Pattern.compile(".*?(\\\\)+.*|.*?(\\.\\.)+.*|.*?Match1.*",Pattern.CASE_INSENSITIVE);
	private final static Pattern attFile=Pattern.compile("attname=(.+)",Pattern.CASE_INSENSITIVE);
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private CsrfService csrfService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		WebUtils.setRequestEnvironment(request,response);

		String uri=request.getRequestURI();

		if(disableUri.matcher(uri).matches()){
			return ;
		}
		String sPath = request.getServletPath();
		String sQry = request.getQueryString();
		String ip="null";
		boolean isCheckPath=true;

		if("/robots.txt".equalsIgnoreCase(sPath)) {
			isCheckPath = false;
		}
//		if("/MP_verify_MPostsPbgUDeeTqn.txt".equalsIgnoreCase(sPath)) {
//			isCheckPath = false;
//		}

		if (isCheckPath && disablePath.matcher(sPath).matches()) {
			try{
				ip= WebUtils.getClientIpAddress();
			}catch (Exception e){

			}
			logger.error("hacker attach:IP="+ip+", path="+sPath+(sQry != null? "?"+sQry:""));
			return;
		}
		if (sQry != null && (disableQry.matcher(sQry).matches()/*||rsPattern.matcher(sPath).matches()*/)){

			try{
				ip=WebUtils.getClientIpAddress();
			}catch (Exception e){

			}
			logger.error("hacker attach:IP="+ip+", path="+sPath+"?"+sQry);
			return;
		}

		try {
			if (!AppBeanInjector.configuration.isOnProd()) {
				String debugClient = request.getParameter("client");
				HttpSession httpSession = request.getSession();
				if (LwxfStringUtils.isEquals(debugClient, "weixin", false)) {
					LwxfUserAgent agent = LwxfUserAgent.parseUserAgentString("MicroMessenger");
					httpSession.setAttribute("user_agent", agent);
				} else if (debugClient != null && debugClient.length() == 0) {
					httpSession.removeAttribute("user_agent");
				}
			}

			// ajax 请求相应头处理
			String xrw = request.getHeader(CommonConstant.REQUEST_HEADER_KEY_AJAX_TAG);
			if (null != xrw && xrw.indexOf(CommonConstant.REQUEST_HEADER_KEY_AJAX_TAG_VALUE) > -1) {
				response.setContentType(WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET);
			}
			if (!AppBeanInjector.configuration.isOnDev()) {
				String referer = request.getHeader("referer");
				if (referer != null && referer.startsWith("http://localhost")) {
					return;
				}
			}

			if(sQry!=null && xrw==null && WebConstant.REQUEST_METHOD_GET.equalsIgnoreCase(request.getMethod())) {
				Matcher attFileMatcher = attFile.matcher(sQry);
				if (attFileMatcher.matches()) {
					String attFileName = attFileMatcher.group(1);
					String tmp = new StringBuilder("attachment; filename=\"").append(attFileName).append("\"; filename*=utf-8' '").append(attFileName).toString();
					response.setHeader("Content-Disposition", tmp);
				}
			}
			filterChain.doFilter(request, response);
			if(request.isRequestedSessionIdFromURL()){
				logger.error("sessionIdFromURL:path="+sPath+"?"+sQry);
			}
		} catch (Exception ex) {
			String errorMsg = WebUtils.getRequestUrl(request) + " IP:" + WebUtils.getClientIpAddress() + " UserId:" + WebUtils.getDataFromRequestMap("HttpCurLoginUserId");
			if (ex instanceof ResNotFoundException) {
				this.logger.error(ex.getMessage() + "\n\r" + errorMsg);
			}
			/*else if (ex instanceof NestedServletException) { // 输出修改backog时产生的事务异常信息
				this.logger.error(errorMsg, ex);
			} else {
				this.logger.error(errorMsg, ex);
			}*/
			//党冬 2017年6月20日　处理上面注释的两个判断打印同一个代码问题
			else {
				this.logger.error(errorMsg, ex);
			}
			if (null == this.csrfService) {
				this.csrfService = (CsrfService) SpringContextUtil.getBean("csrfService");
			}
			request.setAttribute("appcfg", this.csrfService.getAppCfg(request));
			request.setAttribute("lwxf.excetion", ex);
			request.getRequestDispatcher("/error").forward(request, response);
		} finally {
//			RequestThreadLocal.clear();
		}
	}
}
