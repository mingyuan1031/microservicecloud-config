package com.lwxf.newstore.webapp.common.utils;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：国际化工具类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 13:39:10
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("i18nUtil")
public class LocalMessageSourceUtil {
	private static final Logger logger = LoggerFactory.getLogger(LocalMessageSourceUtil.class);
	@Autowired
	private MessageSource messageSource;
	/**
	 * 
	 String getMessage(String var1, Object[] var2, String var3, Locale var4);
	 String getMessage(String var1, Object[] var2, Locale var3) throws NoSuchMessageException;
	 String getMessage(MessageSourceResolvable var1, Locale var2) throws NoSuchMessageException;
	 */
	/**
	 * @param key ：对应messages配置的key.
	 * @return
	 */
	public String getMessage(String key){
		return this.getMessage(key,new Object[]{});
	}
	public String getMessage(String key,String defaultMessage){
		return this.getMessage(key, null,defaultMessage);
	}
	public String getMessage(String key,String defaultMessage,Locale locale){
		return this.getMessage(key, null,defaultMessage,locale);
	}
	public String getMessage(String key,Locale locale){
		return this.getMessage(key,null,"",locale);
	}
	/**
	 *
	 * @param key ：对应messages配置的key.
	 * @param args : 数组参数.
	 * @return
	 */
	public String getMessage(String key,Object[] args){
		return this.getMessage(key, args,"");
	}
	public String getMessage(String key,Object[] args,Locale locale){
		return this.getMessage(key, args,"",locale);
	}
	/**
	 *
	 * @param key ：对应messages配置的key.
	 * @param args : 数组参数.
	 * @param defaultMessage : 没有设置key的时候的默认值.
	 * @return
	 */
	public String getMessage(String key,Object[] args,String defaultMessage){
		//根据获取用户对象上的language设置locale,如果取不到用户信息，取浏览器语言信息
		Locale locale;

		User user = null;
		try {
			user = WebUtils.getCurrUser();
		}catch (Exception ex){
			logger.info("获取用户语言方法，用户未登录，属于正常日志");
		}
		String language = null;
		if (user != null) {
			language = WebUtils.getCurrUser().getLanguage();
		}
		if (language == null) {
			locale = LocaleContextHolder.getLocale();
		} else {
			logger.debug("language = ".concat(language));
			String[] languageArray = language.split("-");
			locale = new Locale(languageArray[0], languageArray[1]);
		}
		//Locale locale1 = new Locale("zh", "CN");

		//这里使用比较方便的方法，不依赖request.
		//别一个实现方式
		//Locale locale1= RequestContextUtils.getLocale(request);
		return this.getMessage(key, args, defaultMessage, locale);
	}
	/**
	 * 指定语言.
	 * @param key
	 * @param args
	 * @param defaultMessage
	 * @param locale
	 * @return
	 */
	public String getMessage(String key,Object[]args,String defaultMessage,Locale locale){
		return messageSource.getMessage(key, args, defaultMessage,locale);
	}

	/**
	 * 获取用户语言信息
	 * @return
	 */
	public String getLanguage(){
		//根据获取用户对象上的language设置locale,如果取不到用户信息，取浏览器语言信息
		Locale locale;
		User user = null;
		try {
			user = WebUtils.getCurrUser();
		}catch (Exception ex){
			logger.info("获取用户语言方法，用户未登录，属于正常日志");
		}
		String language = null;
		if (user != null) {
			language = WebUtils.getCurrUser().getLanguage();
		}
		if (language == null) {
			locale = LocaleContextHolder.getLocale();
		} else {
			String[] languageArray = language.split("-");
			locale = new Locale(languageArray[0], languageArray[1]);
		}
		return locale.getLanguage();
	}
}