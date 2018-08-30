package com.lwxf.newstore.webapp.common.utils;

import com.lwxf.commons.uniquekey.IdGererateFactory;

/**
 * 功能：数据库主键生成工具
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 18:08:24
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class UniqueKeyUtil {
	private static IdGererateFactory gererateFactory;
	static {
		gererateFactory = (IdGererateFactory)SpringContextUtil.getBean("idGererateFactory");
	}
	public static long getId(){
		return gererateFactory.nextId();
	}

	public static String getStringId(){
		return gererateFactory.nextStringId();
	}
}
