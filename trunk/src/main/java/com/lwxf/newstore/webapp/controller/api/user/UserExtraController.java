package com.lwxf.newstore.webapp.controller.api.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;
import com.lwxf.newstore.webapp.facade.user.UserFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:34:55
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/users/0/password", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class UserExtraController {
	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@PutMapping(value = "")
	public RequestResult updatePassword(@RequestBody MapContext userExtraMap) {
		String newPassword = userExtraMap.getTypedValue("newpassword", String.class);
		Map<String, Object> errormap = new HashMap<>();
		if (LwxfStringUtils.isBlank(newPassword)) {
			errormap.put("newpassword", ErrorCodes.VALIDATE_INVALID_PASSWORD);
		}
		if (errormap.size() > 0) {
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errormap);
		}
		userExtraMap.remove("newpassword");
		userExtraMap.put("password", newPassword);
		RequestResult requestResult = UserExtra.validateFields(userExtraMap);
		if (requestResult != null) {
			return requestResult;
		}
		return userFacade.updateUserPassword(WebUtils.getCurrUserId(), newPassword);
	}

	@GetMapping
	public RequestResult getPasswordState(){
		return userFacade.getPasswordState();
	}
}
