package com.lwxf.newstore.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lwxf.commons.uniquekey.IdGererateFactory;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:08:08
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class UniqueKeyConfig {
	@Value("${lwxf.uniquekey.workerId}")
	private Long workId;
	@Value("${lwxf.uniquekey.datacenterId}")
	private Long datacenterId;

	@Bean
	public IdGererateFactory idGererateFactory() {
		return new IdGererateFactory(this.workId);
	}
}
