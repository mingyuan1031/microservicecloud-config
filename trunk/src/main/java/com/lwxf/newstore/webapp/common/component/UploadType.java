package com.lwxf.newstore.webapp.common.component;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:43:59
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum UploadType {
	TEMP(0),
	FORMAL(1);
	private int value;
	UploadType(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
