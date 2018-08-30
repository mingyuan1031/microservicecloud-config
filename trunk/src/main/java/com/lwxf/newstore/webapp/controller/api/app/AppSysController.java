package com.lwxf.newstore.webapp.controller.api.app;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:20:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class AppSysController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/sys/errors")
	public RequestResult errorsOut(@RequestBody Map<String, Object> errors) {
		if (null == errors) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_REQUEST_PARAM_ERROR_10000, "错误信息不能为空");
		}
		String type = (String) errors.get("type");
		boolean isPrint = true;
		if (LwxfStringUtils.isBlank(type)) {
			isPrint = false;
			if (AppBeanInjector.configuration.isOnDev()) {
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_REQUEST_PARAM_ERROR_10000, "请输入type值");
			}
		}
		Object content = errors.get("content");
		if (null == content) {
			isPrint = false;
			if (AppBeanInjector.configuration.isOnDev()) {
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_REQUEST_PARAM_ERROR_10000, "请输入content值");
			}
		}
		if (isPrint) {
			this.printErrors(type, content);
		}
		return ResultFactory.generateSuccessResult();
	}

	@GetMapping("/sys/touch")
	public String touch() {
		/*String userId = WebUtils.getCurrUserId();
		if (userId != null) {
			String sessionId = WebUtils.getHttpSession().getId();
			String key = WebConstant.MQ_TOKEN_PREFIX.concat(sessionId);
		}*/
		return WebConstant.REQUEST_RESULT_AJAX_EMPTY_OBJ;
	}

	private final static String VALIDATION_ERROR_TEMPLATE = "************** {0} ：{1}";

	private void printErrors(String type, Object errors) {
		this.logger.error("************** 前端错误信息输出 **************");
		this.logger.error("************** type ：" + type);
		this.logger.error("************** content >>>>>>");
		if (ErrorType.isValidation(type)) {
			Map<String, Object> temp = (Map<String, Object>) errors;
			this.logger.error("************** res ：" + temp.get("res"));
			Map<String, Object> errorInfo = (Map<String, Object>) temp.get("fields");
			for (Map.Entry<String, Object> entry : errorInfo.entrySet()) {
				this.logger.error(LwxfStringUtils.format(VALIDATION_ERROR_TEMPLATE, entry.getKey(), entry.getValue()));
			}
		} else if (ErrorType.isJs(type)) {
			this.logger.error(errors.toString());
		} else {
			this.logger.error("************** 未定义的错误类型 type ：" + type);
		}
		this.logger.error("**********************************************");
	}
}

enum ErrorType {
	validation("sys.error.validation"),
	js("sys.error.js");
	private String value;

	ErrorType(String value) {
		this.value = value;
	}

	static boolean isValidation(String type) {
		return validation.value.equals(type);
	}

	static boolean isJs(String type) {
		return js.value.equals(type);
	}
}
