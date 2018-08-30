package com.lwxf.newstore.webapp.controller.path;

import java.util.Map;

import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:50:41
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LoadBaseCfgAndSysCfgController extends LoadBaseCfgController {
	@Override
	protected void loadSysConfig(Map<String, Object> cfg) {
		AppBeanInjector.configuration.getSystemConfigJson(cfg);
	}
}
