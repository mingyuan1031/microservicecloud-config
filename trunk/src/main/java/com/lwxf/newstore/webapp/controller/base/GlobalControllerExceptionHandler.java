package com.lwxf.newstore.webapp.controller.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lwxf.commons.constant.CommonConstant;
import com.lwxf.commons.exception.LwxfException;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.utils.ExceptionGenerateFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;

import static com.lwxf.commons.exception.ErrorCodes.SYS_ERROR_REQUEST_METHOD_NOT_SUPPORT_00020;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:10:55
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class.getName());
	private static final DefaultExceptionHanler DEFAULT_EXCEPTION_HANLER = new DefaultExceptionHanler();
	private static final LwxfExceptionHanler CH_EXCEPTION_HANLER = new LwxfExceptionHanler();
	private static final Map<String,AbstractCustomExceptionHandler> exceptionMapping = new HashMap<String,AbstractCustomExceptionHandler>(){{
		put(HttpRequestMethodNotSupportedException.class.getName(),new HttpRequestMethodNotSupportedExceptionHandler());
	}};

	private AbstractCustomExceptionHandler getCustomExceptionHandler(Exception ex){
		AbstractCustomExceptionHandler handler = exceptionMapping.get(ex.getClass().getName());
		if(handler == null){
			if(ex instanceof LwxfException){
				return CH_EXCEPTION_HANLER;
			}
			return DEFAULT_EXCEPTION_HANLER;
		}
		return handler;
	}

	@ExceptionHandler(Exception.class)
	public void handleConflict(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
		AbstractCustomExceptionHandler handler = this.getCustomExceptionHandler(ex);
		handler.doException(request,response,ex);
		request.getRequestDispatcher("/error").forward(request, response);
	}

	/**
	 * 异常处理器定义
	 */
	static abstract class AbstractCustomExceptionHandler{
		protected void doException(HttpServletRequest request, HttpServletResponse response, Exception ex){
			this.changeResponseStatus(response);
			logger.error("************* 输出异常堆栈",ex);
			Exception tmpEx = this.createLwxfException(ex);
			request.setAttribute("lwxf.excetion", tmpEx);
			// 输出日志
			StringBuilder sb = new StringBuilder();
			LwxfStringUtils.concat(sb,"       ================ api 请求异常 ================");
			LwxfStringUtils.concat(sb,"********************** Cause : ", tmpEx.getMessage());
			LwxfStringUtils.concat(sb,"*************** Request Path : ", request.getServletPath());
			LwxfStringUtils.concat(sb,"************* Request Method : ", request.getMethod());
			LwxfStringUtils.concat(sb,"*************      UserAgent : ", WebUtils.getOriginalUserAgent());
			LwxfStringUtils.concat(sb,"*************        Referer : ", request.getHeader("Referer"));
			LwxfStringUtils.concat(sb,"*************    X-PHPSESSID : ", request.getHeader(CommonConstant.REQUEST_HEADER_KEY_X_PHPSESSIONID));
			LwxfStringUtils.concat(sb,"*************          X-SID : ", request.getHeader(CommonConstant.REQUEST_HEADER_KEY_X_SID));
			LwxfStringUtils.concat(sb,"*************      sessionId : ", request.getSession().getId());

			Cookie[] cookies = request.getCookies();
			StringBuilder cookieSb = new StringBuilder();
			if(null != cookies){
				for (Cookie cookie : cookies) {
					cookieSb.append(cookie.getName()).append(" = ").append(cookie.getValue()).append("|");
				}
			}
			LwxfStringUtils.concat(sb,"      Cookie      : ", cookieSb.length()==0?"没有任何cookie信息":cookieSb.toString());
			logger.error(sb.toString());
		}

		protected abstract void changeResponseStatus(HttpServletResponse response);

		protected Exception createLwxfException(Exception ex){
			return ex;
		}
	}

	static class DefaultExceptionHanler extends AbstractCustomExceptionHandler {
		@Override
		protected void doException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
			StringBuilder sb = new StringBuilder();
			LwxfStringUtils.concat(sb,"  **************** 出现未拦截的异常，请处理 **************** ");
			LwxfStringUtils.concat(sb,"  Exception Class : ", ex.getClass().getName());
			super.doException(request, response, ex);
		}

		@Override
		protected void changeResponseStatus(HttpServletResponse response) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
	}

	/**
	 * 创海自定义异常处理
	 */
	static class LwxfExceptionHanler extends AbstractCustomExceptionHandler {
		@Override
		protected void doException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
			StringBuilder sb = new StringBuilder();
			LwxfStringUtils.concat(sb,"  **************** 创海自定义异常 **************** ");
			LwxfStringUtils.concat(sb,"  Exception Class : ", ex.getClass().getName());
			LwxfStringUtils.concat(sb,"Exception Message : ", ex.getMessage());
			request.setAttribute("lwxf.excetion", ex);
			//this.changeResponseStatus(response);
		}

		@Override
		protected void changeResponseStatus(HttpServletResponse response) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
	}

	/**
	 * 请求method与api不匹配
	 */
	static class HttpRequestMethodNotSupportedExceptionHandler extends AbstractCustomExceptionHandler{
		@Override
		protected void changeResponseStatus(HttpServletResponse response) {
			//response.setStatus(HttpResponseStatus.METHOD_NOT_ALLOWED.getCode());
			response.setStatus(HttpStatus.OK.value());
		}

		@Override
		protected Exception createLwxfException(Exception ex) {
			return ExceptionGenerateFactory.createException(SYS_ERROR_REQUEST_METHOD_NOT_SUPPORT_00020,ex);
		}
	}
}
