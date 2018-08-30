package com.lwxf.newstore.webapp.common.mobile.weixin.service;

import org.springframework.stereotype.Component;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("weixinJsonMsgService")
public class JsonMsgService extends BaseMsgService {
	@Override
	protected String getUrl() {
		return "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={0}";
	}
}
