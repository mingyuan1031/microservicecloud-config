package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:47
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class TextJsonMsg extends BaseJsonMsg {
	Map<String,String> text = new HashMap<>();
	public TextJsonMsg(){
		this.setMsgtype("text");
	}

	public Map<String, String> getText() {
		return text;
	}

	@Override
	public void setContentInfo(String content) {
		this.text.put("content",content);
	}
}
