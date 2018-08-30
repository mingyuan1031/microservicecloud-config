package com.lwxf.newstore.webapp.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.lwxf.newstore.webapp.common.authcode.AuthCodeServlet;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:32:45
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class ServletConfig implements WebMvcConfigurer {
	@Bean
	public ServletRegistrationBean authCodeServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new AuthCodeServlet());
		bean.setName("authCodeServlet");
		bean.addUrlMappings("/authCode");
		bean.setOrder(1);
		return bean;
	}

	/**
	 * 数据库连接池监控
	 * @return
	 */
	@Bean
	public ServletRegistrationBean druidServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet());
		bean.setName("DruidStatView");
		bean.addUrlMappings("/druid/*");
		bean.setOrder(2);
		return bean;
	}
}
