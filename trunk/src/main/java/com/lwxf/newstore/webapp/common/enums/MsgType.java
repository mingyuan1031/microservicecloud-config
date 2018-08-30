package com.lwxf.newstore.webapp.common.enums;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:58:21
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum MsgType {
	//站内信
	MAIL("mail"),
	//电子邮件
	EMAIL("email"),
	//微信
	WEIXIN("weixin"),
	//手机
	MOBILE("mobile");

	private String value;

	MsgType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static MsgType transform(String type) {
		if(type == null){
			return null;
		}
		MsgType[] msgTypeList = MsgType.values();
		for(MsgType msgType : msgTypeList){
			if(msgType.getValue().equals(type)){
				return msgType;
			}
		}
		return null;
	}
}
