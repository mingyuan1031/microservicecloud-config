package com.lwxf.newstore.webapp.common.mobile.weixin.service;

import com.lwxf.newstore.webapp.common.mobile.IMobileMsg;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:33
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface IMsgService {
	void pushMsg(final IMobileMsg msgTemplate);
}
