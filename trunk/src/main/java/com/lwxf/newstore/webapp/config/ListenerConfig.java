package com.lwxf.newstore.webapp.config;

import org.owasp.csrfguard.CsrfGuardHttpSessionListener;
import org.owasp.csrfguard.CsrfGuardServletContextListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lwxf.newstore.webapp.baseservice.security.csrf.LwxfCsrfGuardServletContextListener;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:52:49
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class ListenerConfig implements WebMvcConfigurer {
	@Bean
	public ServletListenerRegistrationBean<CsrfGuardServletContextListener> csrfGuardServletContextListener(){
		ServletListenerRegistrationBean<CsrfGuardServletContextListener> bean = new ServletListenerRegistrationBean<>(new LwxfCsrfGuardServletContextListener());
		bean.setOrder(1);
		return bean;
	}

	@Bean
	public ServletListenerRegistrationBean<CsrfGuardHttpSessionListener> csrfGuardHttpSessionListener(){
		ServletListenerRegistrationBean<CsrfGuardHttpSessionListener> bean = new ServletListenerRegistrationBean<>(new CsrfGuardHttpSessionListener());
		bean.setOrder(2);
		return bean;
	}

}
