package com.lwxf.newstore.webapp.common.security.attacklocker;

import com.lwxf.commons.security.attacklocker.IKeyGetter;
import com.lwxf.newstore.webapp.common.utils.WebUtils;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:55:18
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class IpKeyGetter implements IKeyGetter<String> {
	@Override
	public String call(Object... args) {
		String key = args[0].toString()+WebUtils.getHttpSession().getId();

		return key + WebUtils.getClientIpAddress();
	}
}
