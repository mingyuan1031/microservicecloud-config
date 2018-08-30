package com.lwxf.newstore.webapp.controller.api.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.ValidateUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.sms.SmsUtil;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.dto.SendAuthenticationCodeDto;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.user.UserThirdInfoFacade;

import static com.lwxf.newstore.webapp.facade.AppBeanInjector.configuration;
import static com.lwxf.newstore.webapp.facade.AppBeanInjector.i18nUtil;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:20:45
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/users/0",produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class UserThirdInfoController {
	@Resource(name = "userThirdInfoFacade")
	private UserThirdInfoFacade userThirdInfoFacade;
	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@GetMapping("/thirdinfo")
	public RequestResult getThirdInfo(){
		return this.userThirdInfoFacade.findUserThirdInfo();
	}
}
