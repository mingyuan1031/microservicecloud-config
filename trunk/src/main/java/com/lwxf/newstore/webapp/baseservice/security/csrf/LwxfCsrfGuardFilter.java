package com.lwxf.newstore.webapp.baseservice.security.csrf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.csrfguard.CsrfGuard;
import org.owasp.csrfguard.http.InterceptRedirectResponse;


import com.lwxf.commons.constant.CommonConstant;
import com.lwxf.newstore.webapp.common.utils.SpringContextUtil;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:02:28
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LwxfCsrfGuardFilter implements Filter {

	private FilterConfig filterConfig = null;
	private CsrfService csrfService;

//	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void destroy() {
		filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		/** only work with HttpServletRequest objects **/
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession(false);

			if (session == null) {
				// If there is no session, no harm can be done
				filterChain.doFilter(httpRequest, response);
				return;
			}

			CsrfGuard csrfGuard = CsrfGuard.getInstance();
			if(!csrfGuard.isProtectedMethod(httpRequest.getMethod())){
				filterChain.doFilter(httpRequest, response);
				return;
			}

//			csrfGuard.getLogger().log(String.format("CsrfGuard analyzing request %s", httpRequest.getRequestURI()));

			InterceptRedirectResponse httpResponse = new InterceptRedirectResponse((HttpServletResponse) response, httpRequest, csrfGuard);

//			 if(MultipartHttpServletRequest.isMultipartRequest(httpRequest)) {
//				 httpRequest = new MultipartHttpServletRequest(httpRequest);
//			 }

			if (session.isNew() && csrfGuard.isUseNewTokenLandingPage()) {
				csrfGuard.writeLandingPage(httpRequest, httpResponse);
			} else if (isValidRequest(csrfGuard, httpRequest, httpResponse)) {
				filterChain.doFilter(httpRequest, httpResponse);
			} else {
				/** invalid request - nothing to do - actions already executed **/
			}

			/** update tokens **/
			csrfGuard.updateTokens(httpRequest);

		} else {
			filterConfig.getServletContext().log(String.format("[WARNING] CsrfGuard does not know how to work with requests of class %s ", request.getClass().getName()));

			filterChain.doFilter(request, response);
		}
	}

	private boolean verifyAjaxToken(HttpServletRequest request, HttpServletResponse response) {
		CsrfGuard csrfGuard = CsrfGuard.getInstance();
		HttpSession session = request.getSession();
		String tokenFromSession = (String) session.getAttribute(csrfGuard.getSessionKey());
		String tokenFromRequest = request.getHeader(csrfGuard.getTokenName());
		boolean ret = tokenFromSession.equals(tokenFromRequest);

		if (!ret) {
			if (null == this.csrfService) {
				this.csrfService = (CsrfService) SpringContextUtil.getBean("csrfService");
			}
			String xSid = request.getHeader(CommonConstant.REQUEST_HEADER_KEY_X_SID);
			ret = this.csrfService.validateToken(request, response, xSid, tokenFromRequest);

		}

		return ret;
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getHeader(CommonConstant.REQUEST_HEADER_KEY_AJAX_TAG) != null;
	}

	public boolean isValidRequest(CsrfGuard csrfGuard, HttpServletRequest request, HttpServletResponse response) {
		/*boolean valid = !csrfGuard.isProtectedPageAndMethod(request);
		if (valid)
			return true;*/
		HttpSession session = request.getSession();
		String tokenFromSession = (String) session.getAttribute(csrfGuard.getSessionKey());

		/** sending request to protected resource - verify token **/
		if (tokenFromSession != null) {
			if (csrfGuard.isAjaxEnabled() && isAjaxRequest(request)) {
				Boolean ret = verifyAjaxToken(request, response);
				if (ret) {
					return true;
				}
			}
		}

		return csrfGuard.isValidRequest(request, response);
	}

	@Override
	public void init(@SuppressWarnings("hiding") FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
}
