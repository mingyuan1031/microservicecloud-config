package com.lwxf.newstore.webapp.config.db;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import com.lwxf.commons.security.EncryptionPropertyPlaceholderConfigurer;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:50:40
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
@Profile("innertest")
public class InnerTestParsePropertiesConfig {
	@Bean
	public static PropertyPlaceholderConfigurer encryptionPropertyPlaceholderConfigurer(){
		EncryptionPropertyPlaceholderConfigurer bean = new EncryptionPropertyPlaceholderConfigurer();
		bean.setLocations(new ClassPathResource("application.properties"),new ClassPathResource("application-innertest.properties"));
		bean.setLocalOverride(true);
		return bean;
	}
}
