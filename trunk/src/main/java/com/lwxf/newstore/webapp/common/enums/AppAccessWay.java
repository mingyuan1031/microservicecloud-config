package com.lwxf.newstore.webapp.common.enums;

/**
 * 功能：访问来源类型
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:32:15
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum AppAccessWay {
	WEB((byte) 0),
	IOS((byte) 1),
	ANDROID((byte) 2),
	WECHAT((byte) 3),
	DINGDING((byte) 4);
	private byte value;

	AppAccessWay(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}
}
