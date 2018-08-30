package com.lwxf.newstore.webapp.common.mobile;

/**
 * 功能：手机端消息的公共接口
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 12:12
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface IMobileMsg {
	void setTouser(String touser);
	String serialized();
	void setUrl(String url);
	String getUrl();
	void setContentInfo(String info);
}
