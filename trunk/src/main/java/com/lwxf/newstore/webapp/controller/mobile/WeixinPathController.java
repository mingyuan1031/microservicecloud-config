package com.lwxf.newstore.webapp.controller.mobile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lwxf.commons.utils.IGetter;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.SyncGet;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.facade.user.UserFacade;
import com.lwxf.newstore.webapp.facade.user.UserThirdInfoFacade;

import static com.lwxf.newstore.webapp.common.constant.WebConstant.REDIRECT_PATH_TEMPLATE;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:14
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Controller
@RequestMapping(value = "/wx")
public class WeixinPathController {
	private static final String TASK_URL_TPL = "/{0}/#/?show=/task/{1}";
	private static final String TASK_COMMENT_URL_TPL = "/{0}/#/?show=/task/{1}/comments";

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "userThirdInfoFacade")
	private UserThirdInfoFacade userThirdInfoFacade;
	@Resource(name = "userFacade")
	private UserFacade userFacade;

	/**
	 * 微信端授权回调url
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/auth")
	public String getAuth(final HttpServletRequest request, final HttpServletResponse response, @RequestParam String wxOpenId) {
		// 同步是为了解决微信多次回调问题
		return SyncGet.exec("weixin_auth_redirectToSavedRequest", new IGetter<String>() {
			@Override
			public String get() {
				if (null != WebUtils.getCurrUserId()) {
					logger.debug("当前用户已登陆直接跳转到主页");
					return WebUtils.redirectToSavedRequest(request, "/mall");
				}
				return userThirdInfoFacade.doWxAuthLogin(wxOpenId);
			}
		});
	}

	@Deprecated // 运行一段时间后删除
	@GetMapping("/orgs/{orgId}/tasks/{taskId}")
	public String wxTaskInfoPath(@PathVariable String orgId, @PathVariable String taskId) {
		String url = LwxfStringUtils.format(TASK_URL_TPL, orgId, taskId);
		return LwxfStringUtils.format(REDIRECT_PATH_TEMPLATE, url);
	}

	@Deprecated // 运行一段时间后删除
	@GetMapping("/orgs/{orgId}/tasks/{taskId}/comments")
	public String wxTaskCommentPath(@PathVariable String orgId, @PathVariable String taskId) {
		String url = LwxfStringUtils.format(TASK_COMMENT_URL_TPL, orgId, taskId);
		return LwxfStringUtils.format(REDIRECT_PATH_TEMPLATE, url);
	}
}
