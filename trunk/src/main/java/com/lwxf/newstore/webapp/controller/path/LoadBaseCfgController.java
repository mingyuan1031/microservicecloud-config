package com.lwxf.newstore.webapp.controller.path;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.baseservice.security.csrf.CsrfService;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:25:11
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class LoadBaseCfgController {
	protected JsonMapper json = new JsonMapper();
	@Resource(name = "csrfService")
	protected CsrfService csrfService;

	@ModelAttribute("preload")
	public String setPreloadData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> cfg = new HashMap<>();
		// 加载基础配置
		Map<String, Object> appcfg = csrfService.getAppCfg(request);
		cfg.put("appcfg", appcfg);
		// 加载系统配置
		this.loadSysConfig(appcfg);
		// 用户预加载数据
		Map<String, Object> prelaod = this.loadUserPreload(request);
		if (null != prelaod && prelaod.size() > 0) {
			cfg.put("preload", prelaod);
		}
		request.setAttribute("domainUrl", AppBeanInjector.configuration.getDomainUrl());
		return LwxfStringUtils.preventXssAttacks(this.json.toJson(cfg));
	}

	protected void loadSysConfig(Map<String, Object> cfg) {
	}

	protected Map<String, Object> loadUserPreload(HttpServletRequest request) {
		return new HashMap<>();
	}

	protected void noCahce(){
		WebUtils.getHttpServletResponse().setHeader("Cache-Control","max-age=0, private, no-cache, no-store,must-revalidate");
	}
}
