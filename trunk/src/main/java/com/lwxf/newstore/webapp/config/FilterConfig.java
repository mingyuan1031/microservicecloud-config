package com.lwxf.newstore.webapp.config;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import com.googlecode.webutilities.filters.CompressionFilter;

import com.lwxf.newstore.webapp.baseservice.security.csrf.LwxfCsrfGuardFilter;
import com.lwxf.newstore.webapp.web.filter.LwxfCharacterEncodingFilter;
import com.lwxf.newstore.webapp.web.filter.LwxfErrorPageFilter;
import com.lwxf.newstore.webapp.web.filter.LwxfFirstFilter;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:06:15
 * @version：2018 1.0
 * @company：老屋新房 Createdwith IntelliJ IDEA
 */
@Configuration
public class FilterConfig implements WebMvcConfigurer {

	@Bean
	public ErrorPageFilter errorPageFilter() {
		return new ErrorPageFilter();
	}

	@Bean
	public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.setEnabled(false);
		return filterRegistrationBean;
	}

	@Bean
	public LwxfErrorPageFilter errorEpmErrorPageFilter() {
		return new LwxfErrorPageFilter();
	}

	@Bean
	public FilterRegistrationBean epmErrorPageFilter(LwxfErrorPageFilter filter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.setOrder(Integer.MIN_VALUE);
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean errorHandlerFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		String name = "epmFirstFilter";
		bean.setFilter(new LwxfFirstFilter());
		bean.addUrlPatterns("/*");
		bean.setName(name);
		bean.setOrder(1);
		return bean;
	}
	@Bean
	public FilterRegistrationBean shiroFilter(ShiroFilterFactoryBean factoryBean) {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		String name = "shiroFilter";
		try {
			Object shiroFilter = factoryBean.getObject();
			bean.setFilter((Filter) shiroFilter);
			bean.addUrlPatterns("/*");
			bean.setName(name);
			bean.setOrder(2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean;
	}

	/**
	 * csrf filter
	 */
	@Bean
	public FilterRegistrationBean CSRFGuard() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		String name = "CSRFGuard";
		bean.setFilter(new LwxfCsrfGuardFilter());
		bean.addUrlPatterns("/*");
		bean.setName(name);
		bean.setOrder(3);
		return bean;
	}
	@Bean
	public FilterRegistrationBean epmCharacterEncodingFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		String name = "encodingFilter";

		LwxfCharacterEncodingFilter filter = new LwxfCharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		filter.setDisableForceEncoding("/resources/font/.*");

		bean.setFilter(filter);
		bean.setName(name);
		bean.addUrlPatterns("/*");
		bean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR);
		bean.setOrder(4);
		return bean;
	}

	@Bean
	public FilterRegistrationBean compressionFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();

		CompressionFilter filter = new CompressionFilter();
		String name = "compressionFilter";

		bean.setFilter(filter);
		bean.setName(name);

		bean.addInitParameter("compressionThreshold", "1024");
		bean.addInitParameter("ignoreURLPattern", ".*\\.(flv|mp3|mpg|woff|eot|ttf|gif|png|jpg|jpeg|woff2)");
		bean.addInitParameter("ignoreMimes", "images/*,video/*, multipart/x-gzip");
		bean.addInitParameter("ignoreUserAgentsPattern", ".*MSIE.*");

		bean.addUrlPatterns("/*");
		bean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR);
		bean.setOrder(5);
		return bean;
	}

	/*@Bean
	public FilterRegistrationBean secureHeadersFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		SecureHeadersFilter filter = new SecureHeadersFilter();
		bean.setFilter(filter);
		bean.setName("secureHeadersFilter");
		bean.addUrlPatterns("*//*");
		bean.setOrder(6);
		return bean;
	}*/
}
