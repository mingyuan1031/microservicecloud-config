package com.lwxf.newstore.webapp.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.lwxf.commons.json.ser.DueBooleanStringSerializer;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.common.utils.SpringContextUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:40:32
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class CommonBeanConfig {
	@Bean
	public SpringContextUtil springContextUtil(){
		return new SpringContextUtil();
	}

	@Bean
	public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(){
		ApplicationListener<ApplicationReadyEvent> bean = new ApplicationListener<ApplicationReadyEvent>() {
			@Override
			public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
				//MySqlTool.initDbObjects();
			}
		};
		return bean;
	}

	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder(){
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(Include.NON_NULL);
		builder.simpleDateFormat(DateUtil.FORMAT_DATETIME);
		//Long、Double类型到前台转为String即加上双引号
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		simpleModule.addSerializer(Float.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Float.TYPE, ToStringSerializer.instance);
		simpleModule.addSerializer(String.class, DueBooleanStringSerializer.instance);
		builder.modulesToInstall(simpleModule);
		return builder;
	}
}
