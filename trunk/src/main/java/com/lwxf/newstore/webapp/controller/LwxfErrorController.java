package com.lwxf.newstore.webapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.csrfguard.util.Writers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lwxf.commons.exception.LwxfException;
import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.exception.IpWhitelistCheckFailed;
import com.lwxf.commons.json.JsonMapper;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.controller.path.LoadBaseCfgController;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:45:49
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Controller
public class LwxfErrorController extends LoadBaseCfgController implements ErrorController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private JsonMapper json = JsonMapper.nonEmptyMapper();

	@Override
	public String getErrorPath() {
		return WebUtils.getErrorPagePath();
	}

	@RequestMapping(value = "/error", method = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
	public String customError(HttpServletRequest request, HttpServletResponse response) {
		if (WebUtils.isAjaxRequest(request)) {
			return doAjaxError(request, response);
		}else {
			return doError(request, response);
		}
	}

	private String doError(HttpServletRequest request, HttpServletResponse response) {
		String uri = (String) request.getAttribute("javax.servlet.error.request_uri");

		Throwable ex = (Exception) request.getAttribute("lwxf.excetion");
		ex = this.findEasyPmExction(ex);

		if(ex != null && ex instanceof IpWhitelistCheckFailed){
			try {
				response.sendRedirect("/errorip");
				return null;
			} catch (IOException e) {
				logger.error("ip白名单错误 :" + uri, e);
				e.printStackTrace();
			}
		}

		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		statusCode = statusCode == null ? Integer.valueOf(HttpStatus.OK.value()) : statusCode;
		if (uri != null && uri.startsWith("/resources")) {
			return null;
		}
		if (statusCode.equals(404)) {
			try {
				response.sendRedirect(WebConstant.REDIRECT_PATH_404);
				return null;
			} catch (IOException e) {
				logger.error("404 redirect error :" + uri, e);
				e.printStackTrace();
			}

		}

		String errorMsg;
		try {
			errorMsg = HttpStatus.valueOf(statusCode).getReasonPhrase();
		} catch (Exception exception) {
			errorMsg = null;
			logger.error("无效的状态码：" + statusCode, exception);
		}
		if (null == ex) {
			ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
		}
		if (null != ex) {
			if (ex instanceof LwxfException) {
				LwxfException easypmException = (LwxfException) ex;
				errorMsg = easypmException.getMessage();
			}
		}

		if (LwxfStringUtils.isEmpty(errorMsg)) {
			errorMsg = "未知错误";
		}
		if(LwxfStringUtils.isBlank(uri)){
			uri = request.getRequestURI();
		}
		request.setAttribute("statusCode", statusCode);
		request.setAttribute("requestUri", uri);
		request.setAttribute("errorMsg", errorMsg);
		request.setAttribute("errorDate", DateUtil.dateTimeToString(new Date()));
		request.setAttribute("errorIP", WebUtils.getClientIpAddress());
		request.setAttribute("homeUrl",request.getContextPath() + '/');
		return this.getErrorPath();
	}

	private String doAjaxError(HttpServletRequest request,HttpServletResponse response) {
		Throwable ex = (Exception) request.getAttribute("lwxf.excetion");
		ex = this.findEasyPmExction(ex);
		String statusCode;
		String errorMsg;
		if (null != ex && (ex instanceof LwxfException)) {
			LwxfException easypmException = (LwxfException) ex;
			statusCode = easypmException.getErrorCode();
			errorMsg = easypmException.getMessage();
		}else{
			statusCode = ErrorCodes.SYS_UNKNOW_00000;
			errorMsg = AppBeanInjector.i18nUtil.getMessage("SYS_UNKNOW_00000");
			this.logger.error("出现未知错误，请尽快处理",ex);
		}
		RequestResult result = ResultFactory.generateErrorResult(statusCode, errorMsg);
		/*String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
		result.put("requestUri",uri);*/
		// response ajax 请求相应头处理
		response.setCharacterEncoding("UTF-8");
		response.setContentType(WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET);

		PrintWriter writer = null;
		try {
			writer = response.getWriter();

			writer.write(this.json.toJson(result));
			writer.flush();
		} catch (Exception e){
			this.logger.error("出现严重问题，请尽快处理",e);
		}finally {
			Writers.close(writer);
		}
		return null;
	}

	private Throwable findEasyPmExction(Throwable ex) {
		if (ex != null && !(ex instanceof LwxfException)) {
			Throwable temp = null;
			if (ex instanceof ServletException) {
				temp = ((ServletException) ex).getRootCause();
				ex = temp == null?ex:temp;
			} else {
				temp = ex.getCause();
				while (temp != null) {
					ex = temp;
					temp = temp.getCause();
				}
			}
		}
		return ex;
	}
}
