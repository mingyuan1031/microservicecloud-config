package com.lwxf.newstore.webapp.baseservice.security.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.owasp.csrfguard.CsrfGuard;
import org.owasp.csrfguard.CsrfGuardException;
import org.owasp.csrfguard.action.AbstractAction;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 12:00:24
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class CsrfRedirectAction extends AbstractAction {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CsrfGuardException csrfe, CsrfGuard csrfGuard) throws CsrfGuardException {
		if (WebUtils.isAjaxRequest(request)) {
			// Tag == 1 登录超时
			response.setHeader("Tag", "2");
			OutputStream out = null;
			try {
				JsonMapper jMapper = JsonMapper.nonEmptyMapper();
				RequestResult result = ResultFactory.generateErrorResult(ErrorCodes.SYS_INVALID_CSRF_TOKEN_00013, AppBeanInjector.i18nUtil.getMessage("SYS_INVALID_CSRF_TOKEN_00013"));
				String json = jMapper.toJson(result);
				byte[] body = json.getBytes(Charset.defaultCharset());
				response.setContentLength(body.length);
				out = new BufferedOutputStream(response.getOutputStream());
				out.write(body);
				out.flush();
				response.setHeader("Content-Encoding", null);
				return;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		String errorPage = getParameter("Page");
		try {
			response.sendRedirect(errorPage);
		} catch (IOException ioe) {
			throw new CsrfGuardException(ioe);
		}
	}
}
