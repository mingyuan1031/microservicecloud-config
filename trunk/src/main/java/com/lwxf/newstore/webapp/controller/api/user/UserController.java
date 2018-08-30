package com.lwxf.newstore.webapp.controller.api.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

import com.alibaba.druid.support.json.JSONUtils;
import com.lwxf.newstore.webapp.common.utils.JsonUtil;
import com.qiniu.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.support.json.JSONUtils;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.exception.UploadFileException;
import com.lwxf.commons.json.JsonMapper;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.ValidateUtils;
import com.lwxf.commons.utils.file.MimeTypeUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.UserActivityEvent;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.app.AppFacade;
import com.lwxf.newstore.webapp.facade.user.UserFacade;

import static com.lwxf.newstore.webapp.common.constant.BizConstant.BIZ_SYS_UNIT_M_TO_BYTE;
import static com.lwxf.newstore.webapp.facade.AppBeanInjector.configuration;

/**
 * 功能：用户信息操作
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:34:55
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/users", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Resource(name = "userFacade")
	private UserFacade userFacade;
	@Resource(name = "appFacade")
	private AppFacade appFacade;

	/**
	 * 根据用户ID查询用户信息
	 * @param ids
	 * @return
	 */
	@GetMapping(value = "/baseinfos")
	public  RequestResult findUserInfoByUserIds(@RequestParam String ids){
		List<String> listId = new ArrayList<>();
		listId= JsonUtil.fromJson(ids,List.class);
		return this.userFacade.findUserInfoByUserIds(listId);
	}

	/**
	 * 设置用户配置信息
	 *
	 * @param userMap
	 * @return
	 */
	@PutMapping(value = "/0")
	public RequestResult updateUserInfo(@RequestBody MapContext userMap) {
		String event;
		//页面上只有姓名、电话可能同时修改
		/*if (userMap.containsKey("name") && userMap.containsKey("mobile")) {
			event = UserActivityEvent.md_name;
			// TODO：
			//UserExtraUtil.saveUserlog(event);
		}else */
		if (userMap.containsKey("name")) {
			event = UserActivityEvent.md_name;
			//去除name空格　
			userMap.put("name", LwxfStringUtils.trimToNull(userMap.getTypedValue("name", String.class)));
			//UserExtraUtil.saveUserlog(event);// TODO：
		} else if (userMap.containsKey("mobile")) {
			event = UserActivityEvent.md_mobile;
			//UserExtraUtil.saveUserlog(event);// TODO：
		} else if (userMap.containsKey("birthday")) {
			event = UserActivityEvent.md_birthday;
			//UserExtraUtil.saveUserlog(event);// TODO：
		} else if (userMap.containsKey("time_zone")) {
			event = UserActivityEvent.md_time_zone;
			//UserExtraUtil.saveUserlog(event);// TODO：
		} else if (userMap.containsKey("language")) {
			event = UserActivityEvent.md_language;
			//UserExtraUtil.saveUserlog(event);// TODO：
		} else if (userMap.containsKey("email")) {
			event = UserActivityEvent.md_email;
			//UserExtraUtil.saveUserlog(event);// TODO：
		} else if (userMap.containsKey("username")) {
			event = UserActivityEvent.md_username;
			//UserExtraUtil.saveUserlog(event);// TODO：
		}
		RequestResult result = User.validateFields(userMap);
		if (result != null) {
			return result;
		}
		userMap.put("id", WebUtils.getCurrUserId());

		return this.userFacade.updateUser(userMap);
	}



	/**
	 * 更新用户头相
	 *
	 * @param files
	 * @return
	 */
	@PutMapping(value = "/0/avatar")
	public RequestResult uploadAvatar(@RequestBody MultipartFile files) {
		long limitSize = 1L*BIZ_SYS_UNIT_M_TO_BYTE * configuration.getUploadAvatarMaxsize();
		if (files.getSize() > limitSize) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_SIZE_LIMIT_10031, AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_SIZE_LIMIT", new Object[]{configuration.getUploadAvatarMaxsize(), "M"}));
		}
		try {
			String inType = MimeTypeUtil.findMagicMimeType(files.getBytes()).toString();
			if (!FileMimeTypeUtil.isAvatarType(inType)) {
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_TYPE_LIMIT_10032, AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_TYPE_LIMIT", new Object[]{configuration.getUploadAvatarRange()}));
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new UploadFileException(e);
		}

		MapContext userMap = MapContext.newOne();
		userMap.put("id", WebUtils.getCurrUserId());

		RequestResult requestResult = User.validateFields(userMap);
		if (requestResult != null) {
			return requestResult;
		}
		//UserExtraUtil.saveUserlog(UserActivityEvent.md_avatar);// TODO：
		return this.userFacade.updateUserAvatar(WebUtils.getCurrUserId(), userMap, files);
	}

	/**
	 * 用户工作台背景设置
	 * @param uploadImage
	 * @return
	 */
	@PutMapping(value = "/0/background")
	public RequestResult uploadBackground(@RequestParam MultipartFile uploadImage) {
		long limitSize = 1L*BIZ_SYS_UNIT_M_TO_BYTE* configuration.getUploadBackgroundMaxsize();
		if (uploadImage.getSize() > limitSize) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_SIZE_LIMIT_10031, AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_SIZE_LIMIT", new Object[]{configuration.getUploadBackgroundMaxsize(), "M"}));
		}
		if (!FileMimeTypeUtil.isLegalImageType(uploadImage)) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_TYPE_LIMIT_10032, AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_TYPE_LIMIT", new Object[]{configuration.getUploadImgMimeRange()}));
		}
		return userFacade.updateUserBackground(WebUtils.getCurrUserId(), uploadImage);
	}

	@DeleteMapping(value = "/0/background")
	public RequestResult deleteBackground() {
		return userFacade.deleteUserBackground(WebUtils.getCurrUserId());
	}

	/**
	 * 获取用户预加载数据
	 *
	 * @return
	 */
	@GetMapping(value = "/0/preload")
	public RequestResult getUserPreloadData(@RequestParam(required = false) String page) {
		return this.appFacade.loadPreloadDatas(page);
	}

	/**
	 * 用户密码授权
	 * @param map
	 * @return
	 */
	@PostMapping("/0/accredit")
	public RequestResult passwordAccredit(@RequestBody Map map) {
		if (!map.containsKey("password") || map.get("password") == null || !(map.get("password") instanceof String)) {
			String errMsg = AppBeanInjector.i18nUtil.getMessage("SYS_ILLEGAL_DATA_00002");
			return ResultFactory.generateErrorResult(ErrorCodes.SYS_ILLEGAL_DATA_00002, errMsg);
		}
		return userFacade.passwordAccredit(map.get("password").toString());
	}

	/**
	 * 用户登录 需要中山核对登录逻辑
	 *
	 * @return
	 */
	@PostMapping(value = "/login")
	public RequestResult userLogin(@RequestBody MapContext userMap, HttpServletRequest request) {
		User currUser = WebUtils.getCurrUser();
		if (currUser != null) {
			RequestResult result = ResultFactory.generateSuccessResult();
			if(currUser.getRole().intValue() == UserRole.MEMBER.getValue()){
				result.put("go", WebConstant.REDIRECT_PATH_MALL);
			}else{
				result.put("go", WebConstant.REDIRECT_PATH_ADMIN);
			}
			return result;
		}

		return this.userFacade.toLogin(userMap,request);

	}

	/**
	 * 用户登录 需要中山核对登录逻辑
	 *
	 * @return
	 */
	@PostMapping(value = "/wxlogin")
	public RequestResult wxLogin(@RequestBody MapContext userMap, HttpServletRequest request) {
		userMap.put("rememberMe",false);
		RequestResult rs = this.userLogin(userMap,request);
		String userId = WebUtils.getCurrUserId();
		if(userId==null) {
			return rs;
		}

		HttpSession session = request.getSession();
		String openId = (String)session.getAttribute("weixin_openid");
		if(!LwxfStringUtils.isEmpty(openId)){
			AppBeanInjector.weixinFacade.bindWeixin(userId, null, openId);
			session.removeAttribute("weixin_openid");
		}
		return rs;
	}

	/**
	 * 重发激活eamil
	 *
	 * @return
	 */
	@PostMapping(value = "/activate/resendemail")
	public RequestResult resendActivateEmail(@RequestBody String email) {
		if (!ValidateUtils.isEmail(email)) {
			Map<String, String> errorMap = new HashMap<>(1);
			errorMap.put("email", ErrorCodes.VALIDATE_INVALID_EMAIL);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errorMap);
		}
		User user = new User();
		user.setEmail(email);
		return this.userFacade.resendEmail(user);
	}


}