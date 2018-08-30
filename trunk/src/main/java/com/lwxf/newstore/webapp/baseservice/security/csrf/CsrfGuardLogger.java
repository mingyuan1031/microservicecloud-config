package com.lwxf.newstore.webapp.baseservice.security.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import org.owasp.csrfguard.CsrfGuard;
import org.owasp.csrfguard.CsrfGuardException;
import org.owasp.csrfguard.action.AbstractAction;
import org.owasp.csrfguard.log.ILogger;
import org.owasp.csrfguard.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:19:56
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class CsrfGuardLogger extends AbstractAction implements ILogger {
	Logger logger = LoggerFactory.getLogger("org.owasp.csrfguard");

	@Override
	public void log(String msg) {
		logger.info(msg);
	}

	@Override
	public void log(LogLevel level, String msg) {
		switch (level) {
			case Trace:
				logger.trace(msg);
				break;
			case Debug:
				logger.debug(msg);
				break;
			case Info:
				logger.info(msg);
				break;
			case Warning:
				logger.warn(msg);
				break;
			case Error:
				logger.error(msg);
				break;
			case Fatal:
				logger.error(msg);
				break;
			default:
				throw new RuntimeException("unsupported log level " + level);
		}
	}

	@Override
	public void log(Exception exception) {
		logger.warn(exception.getLocalizedMessage(), exception);
	}

	@Override
	public void log(LogLevel level, Exception exception) {
		switch (level) {
			case Trace:
				logger.trace(exception.getLocalizedMessage(), exception);
				break;
			case Debug:
				logger.debug(exception.getLocalizedMessage(), exception);
				break;
			case Info:
				logger.info(exception.getLocalizedMessage(), exception);
				break;
			case Warning:
				logger.warn(exception.getLocalizedMessage(), exception);
				break;
			case Error:
				logger.error(exception.getLocalizedMessage(), exception);
				break;
			case Fatal:
				logger.error(exception.getLocalizedMessage(), exception);
				break;
			default:
				throw new RuntimeException("unsupported log level " + level);
		}

	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CsrfGuardException csrfe, CsrfGuard csrfGuard) throws CsrfGuardException {
		String logMessage = getParameter("Message");

		/** Exception Information **/
		logMessage = logMessage.replaceAll("%exception%", String.valueOf(csrfe));
		logMessage = logMessage.replaceAll("%exception_message%", csrfe.getLocalizedMessage());

		/** Remote Network Information **/
		logMessage = logMessage.replaceAll("%remote_ip%", WebUtils.getClientIpAddress());
		logMessage = logMessage.replaceAll("%remote_host%", request.getRemoteHost());
		logMessage = logMessage.replaceAll("%remote_port%", String.valueOf(request.getRemotePort()));

		/** Local Network Information **/
		logMessage = logMessage.replaceAll("%local_ip%", request.getLocalAddr());
		logMessage = logMessage.replaceAll("%local_host%", request.getLocalName());
		logMessage = logMessage.replaceAll("%local_port%", String.valueOf(request.getLocalPort()));

		/** Requested Resource Information **/
		logMessage = logMessage.replaceAll("%request_uri%", WebUtils.getRequestUrl(request));
		logMessage = logMessage.replaceAll("%request_url%", request.getRequestURL().toString());

		logMessage = logMessage.replaceAll("%user%", ShiroUtil.getCurrUserId() + "");
		String refer=request.getHeader("referer");
		logMessage = logMessage.replaceAll("%refer%",refer==null?"":refer );
		String  tokenFromRequest=request.getHeader(CsrfGuard.getInstance().getTokenName());
		logMessage = logMessage.replaceAll("%tokenFromRequest%",tokenFromRequest==null?"":tokenFromRequest );
		String tokenFromSession=(String)request.getSession().getAttribute(CsrfGuard.getInstance().getSessionKey());
		logMessage = logMessage.replaceAll("%tokenFromSession%",tokenFromSession==null?"":tokenFromSession );


		csrfGuard.getLogger().log(LogLevel.Error, logMessage);
	}
}
