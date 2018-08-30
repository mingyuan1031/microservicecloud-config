package com.lwxf.newstore.webapp.common.enums;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 13:46:01
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum WeixinBindState {
	UNBIND(Byte.valueOf("0")),
	BIND(Byte.valueOf("1"));

	private Byte value;

	WeixinBindState(Byte value){
		this.value = value;
	}

	public Byte getValue() {
		return value;
	}
}
