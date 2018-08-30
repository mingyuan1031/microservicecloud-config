package com.lwxf.newstore.webapp.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:27:59
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
//@Configuration
//@ConditionalOnProperty("ch.enableCache")
public class ServiceBeanConfig {
	private boolean enableCache;
	/*@Resource(name = "projectDao")
	private ProjectDao projectDao;

	@Resource(name = "projectDaoCache")
	private ProjectDao projectCacheDao;

	public boolean isEnableCache() {
		return enableCache;
	}

	public void setEnableCache(boolean enableCache) {
		this.enableCache = enableCache;
	}

	@Bean
	public ProjectService projectService() {
		ProjectServiceImpl projectService = new ProjectServiceImpl();
		if (enableCache) {
			projectService.setDao(projectCacheDao);
		} else {
			projectService.setDao(projectDao);
		}
		return projectService;
	}*/
}
