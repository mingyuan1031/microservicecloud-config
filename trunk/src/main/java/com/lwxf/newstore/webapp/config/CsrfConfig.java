package com.lwxf.newstore.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lwxf.newstore.webapp.baseservice.security.csrf.CsrfService;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:23:37
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class CsrfConfig {
	@Bean
	public CsrfService csrfService(){
		return new CsrfService();
	}
}
