package com.lwxf.newstore.webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:42:56
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
@EnableTransactionManagement
/*@MapperScan("com.lwxf.newstore.webapp.domain.dao")*/
@ImportResource("classpath:/mybatis/config.xml")
public class MyBatisConfiguration {

	/*@Bean
	public PageHelper pageHelper(){
		PageHelper pageHelper = new PageHelper();
		Properties p = new Properties();
		p.setProperty("offsetAsPageNum", "true");
		p.setProperty("rowBoundsWithCount", "true");
		p.setProperty("reasonable", "true");
		pageHelper.setProperties(p);
		return pageHelper;
	}*/
}
