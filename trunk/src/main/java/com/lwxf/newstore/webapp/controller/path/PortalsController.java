package com.lwxf.newstore.webapp.controller.path;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.commons.collections.map.HashedMap;

import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：前端页面跳转控制（无需用户登陆的页面）
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:34:30
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Controller
public class PortalsController extends LoadBaseCfgAndSysCfgController {
	@Override
	protected Map<String, Object> loadUserPreload(HttpServletRequest request) {
		Map<String, Object> userPrelaod = new HashedMap();
		User currUser = ShiroUtil.getCurrUser();
		if(null != currUser){
			userPrelaod.put("user",currUser);
		}
		return userPrelaod;
	}

	@GetMapping("/404")
	public String goTo404() {
		return WebUtils.getError404PagePath();
	}

	@RequestMapping("/wxpay/notify")
	public String goWeixinPayNotify() {
		return "payment/notify";
	}
}
