package com.lwxf.newstore.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:40:09
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@SpringBootApplication
public class WebAppStart extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WebAppStart.class);
	}

	//
    public static void main(String[] args) {
        SpringApplication.run(WebAppStart.class, args);
        //MySqlTool.initDbObjects();
    }
}
