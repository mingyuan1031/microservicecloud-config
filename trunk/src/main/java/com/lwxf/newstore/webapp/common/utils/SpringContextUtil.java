package com.lwxf.newstore.webapp.common.utils;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:25:27
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SpringContextUtil implements ApplicationContextAware, ServletContextAware {

	// Spring应用上下文环境
	private static ApplicationContext applicationContext;

	public static ServletContext getServletContext() {
		return servletContext;
	}

	private static ServletContext servletContext;

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 *
	 * @param applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 获取对象
	 * 这里重写了bean方法，起主要作用
	 *
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		SpringContextUtil.servletContext=servletContext;
		servletContext.setAttribute("homeUrl", servletContext.getContextPath() + '/');
		servletContext.setAttribute("titlePostfix", " - EasyPM");
	}
}
